package com.bex.cpi_search.service;

import com.bex.cpi_search.model.BLSApiRequest;
import com.bex.cpi_search.model.BLSApiResponse;
import com.bex.cpi_search.model.CPIData;
import com.bex.cpi_search.model.DataPoint;
import com.bex.cpi_search.model.Results;
import com.bex.cpi_search.model.Series;
import com.bex.cpi_search.repository.RedisRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service class for managing CPI (Consumer Price Index) data. */
@Service
public class CPIDataService {

  @Autowired private BLSApiService blsApiService;

  @Autowired private RedisRepository<String, CPIData> redisRepository;

  private static final Logger log = LoggerFactory.getLogger(BLSApiService.class);

  /**
   * Retrieves CPI values and notes for specific month, year, and series IDs.
   *
   * <p>The method first attempts to fetch data from Redis cache. If data is not found in the cache,
   * it fetches data from the API for the full year, caches the data, and then retrieves the data
   * from the cache again.
   *
   * @param year the year of the CPI data
   * @param month the month of the CPI data
   * @param seriesIds the list of series IDs
   * @return a map of series IDs to CPIData
   * @throws IOException if an error occurs during the API request
   */
  public Map<String, CPIData> getCPIData(
      final String year, final String month, final List<String> seriesIds) throws IOException {
    log.info("Retrieving CPI data for year: {}, month: {}, seriesIds: {}", year, month, seriesIds);

    // First, try to fetch data from cache
    Map<String, CPIData> cachedData = fetchCachedData(year, month, seriesIds);

    if (cachedData.size() == seriesIds.size()) {
      log.info("Successfully retrieved all requested data from cache.");
      return cachedData;
    }

    log.info("Not all requested data found in cache. Fetching data from API...");

    boolean cacheSuccess = this.fetchAndCacheFromAPI(year, seriesIds);
    if(cacheSuccess){
        // Retrieve data from cache again
        Map<String, CPIData> finalResult = fetchCachedData(year, month, seriesIds);

        // Check if all requested data is now available
        if (finalResult.size() != seriesIds.size()) {
          log.error("Data not found for all requested series IDs after fetching from API.");
          throw new RuntimeException("Data not found for all requested series IDs.");
        }

        log.info("Successfully retrieved CPI data for series IDs: {}", finalResult.keySet());
        return finalResult;
    } else{
      throw new IOException("Failed to fetch data from API and cache it.");
    }
    
  }

  /**
   * Fetches CPI data for the full year from the API and caches the data month by month for each
   * series.
   *
   * @param year the year of the CPI data
   * @param seriesIds the list of series IDs
   * @return true if the data was successfully cached, false otherwise
   * @throws IOException if an error occurs during the API request
   */
  private Boolean fetchAndCacheFromAPI(final String year, final List<String> seriesIds)
      throws IOException {
    log.info("Fetching CPI data from API for year: {}, seriesIds: {}", year, seriesIds);

    // Create a single API request for all series IDs
    BLSApiRequest request = createBLSApiRequest(year, seriesIds);
    String responseJson = blsApiService.fetchData(request);

    BLSApiResponse blsResponse = parseBLSApiResponse(responseJson);

    // Extract CPI data from the API response
    List<CPIData> cpiDataList = this.extractCPIDataList(blsResponse.getResults());

    boolean success = true;

    // Cache each CPIData instance in Redis
    for (CPIData cpiData : cpiDataList) {
      String cacheKey = generateCacheKey(year, cpiData.getMonth(), cpiData.getSeriesId());
      redisRepository.saveDocument(cacheKey, cpiData);
      log.info("Cached CPIData with key: {}", cacheKey);
    }

    return success;
  }

  /**
   * Retrieves CPI data from Redis for the specified year, month, and series IDs.
   *
   * @param year the year of the CPI data
   * @param month the month of the CPI data
   * @param seriesIds the list of series IDs
   * @return a map of series IDs to CPIData retrieved from cache
   */
  private Map<String, CPIData> fetchCachedData(
      final String year, final String month, final List<String> seriesIds) {
    log.info(
        "Fetching CPI data from cache for year: {}, month: {}, seriesIds: {}",
        year,
        month,
        seriesIds);

    Map<String, CPIData> result = new HashMap<>();

    for (String seriesId : seriesIds) {
      String cacheKey = generateCacheKey(year, month, seriesId);
      CPIData cachedData = redisRepository.getDocument(cacheKey);

      if (cachedData != null) {
        result.put(seriesId, cachedData);
        log.info("Found cached data for series ID: {}, year: {}, month: {}", seriesId, year, month);
      }
    }

    return result;
  }

  /**
   * Creates a BLS API request for a given year and series IDs.
   *
   * @param year the year for the data request
   * @param seriesIds the list of series IDs
   * @return a BLSApiRequest object
   */
  private BLSApiRequest createBLSApiRequest(final String year, final List<String> seriesIds) {
    BLSApiRequest request = new BLSApiRequest();
    request.setSeriesIds(seriesIds);
    request.setStartYear(year);
    request.setEndYear(year);
    return request;
  }

  /**
   * Parses the BLS API response JSON string into a BLSApiResponse object.
   *
   * @param responseJson the response JSON string
   * @return a BLSApiResponse object
   * @throws IOException if there is a problem parsing the JSON
   */
  private BLSApiResponse parseBLSApiResponse(final String responseJson) throws IOException {
    // Initialize the ObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();

    // Configure the ObjectMapper to ignore unknown properties (in case there are
    // extra fields in the JSON)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    JsonNode rootNode = objectMapper.readTree(responseJson);

    /* Here we SHOULD be instantiating my model classes with just this one line but unfortunately
    I did not have time to get that working properly. */
    // BLSApiResponse response = objectMapper.readValue(responseJson, BLSApiResponse.class);

    // Log the deserialized response
    String status = rootNode.get("status").asText();
    int responseTime = rootNode.get("responseTime").asInt();
    List<String> messages =
        objectMapper.convertValue(rootNode.get("message"), new TypeReference<List<String>>() {});

    JsonNode resultsNode = rootNode.get("Results");
    Results results = null;
    if (resultsNode != null && !resultsNode.isNull()) {
      results = objectMapper.treeToValue(resultsNode, Results.class);
    }

    // Set the extracted values to your BLSApiResponse object
    BLSApiResponse response = new BLSApiResponse();
    response.setStatus(status);
    response.setResponseTime(responseTime);
    response.setMessage(messages);
    response.setResults(results);

    log.info("Deserialized Response: " + response);

    log.info("Results list: " + response.getResults());

    return response;
  }

  /**
   * Extracts a list of CPIData instances from a list of Results.
   *
   * @param results A Results instance
   * @return A list of CPIData instances.
   */
  public List<CPIData> extractCPIDataList(Results results) {
    List<CPIData> cpiDataList = new ArrayList<>();
    log.info("Extarcting cpiData from results: " + results);
    // Iterate through each Series
    for (Series series : results.getSeries()) {
      log.info("Series: "+ series);
      if (series.getData() == null) {
        log.warn("No data found for series ID: {}", series.getSeriesID());
        continue;
      }

      // Iterate through each DataPoint
      for (DataPoint dataPoint : series.getData()) {
        log.info("datapoint: " + dataPoint);
        CPIData cpiData = new CPIData();

        // Set the fields for CPIData
        cpiData.setSeriesId(series.getSeriesID());
        cpiData.setYear(dataPoint.getYear());
        cpiData.setMonth(dataPoint.getPeriodName());
        cpiData.setCPIValue(dataPoint.getValue());

        // Extract footnotes as a list of strings
        List<String> footnotesList =
            dataPoint.getFootnotes().stream()
                .map(
                    footnote ->
                        String.format("Code: %s, Text: %s", footnote.getCode(), footnote.getText()))
                .collect(Collectors.toList());
        cpiData.setNotes(footnotesList);

        // Log the extracted CPIData
        log.info("Extracted CPIData: {}", cpiData);

        // Add the CPIData to the list
        cpiDataList.add(cpiData);
      }
    }

    // Log the final size of the CPIData list
    log.info("Total CPIData extracted: {}", cpiDataList.size());

    return cpiDataList;
  }

  /**
   * Generates a cache key for the specified year, month, and series ID.
   *
   * @param year the year
   * @param month the month
   * @param seriesId the series ID
   * @return the cache key
   */
  private String generateCacheKey(final String year, final String month, final String seriesId) {
    return String.format("%s-%s-%s", year, month, seriesId);
  }
}
