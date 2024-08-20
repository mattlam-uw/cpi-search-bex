package com.bex.cpi_search.service;

import com.bex.cpi_search.model.BLSApiRequest;
import com.bex.cpi_search.model.BLSApiResponse;
import com.bex.cpi_search.model.CPIData;
import com.bex.cpi_search.model.DataPoint;
import com.bex.cpi_search.model.Results;
import com.bex.cpi_search.repository.RedisRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
   * Retrieves the CPI values and notes for a specific month, year, and series IDs.
   *
   * @param year the year of the CPI data
   * @param month the month of the CPI data
   * @param seriesIds the list of series IDs
   * @return a map of series IDs to CPIData
   */
  public Map<String, CPIData> getCPIData(
      final String year, final String month, final List<String> seriesIds) throws IOException {

    log.info("Retrieving CPI data for year: {}, month: {}, seriesIds: {}", year, month, seriesIds);

    // Create a single API request for all series IDs
    BLSApiRequest request = createBLSApiRequest(year, seriesIds);
    String responseJson = blsApiService.fetchData(request);

    BLSApiResponse blsResponse = parseBLSApiResponse(responseJson);

    List<DataPoint> dataList = extractDataForYear(blsResponse, year);

    Map<String, CPIData> result = new HashMap<>();

    for (String seriesId : seriesIds) {
      Optional<DataPoint> dataForMonth =
          dataList.stream().filter(data -> data.getPeriod().equals(month)).findFirst();

      if (dataForMonth.isPresent()) {
        DataPoint data = dataForMonth.get();
        CPIData cpiData = new CPIData();
        cpiData.setCPIValue(data.getValue());
        cpiData.setFootnotes(data.getFootnotes());

        String cacheKey = generateCacheKey(year, month, seriesId);
        redisRepository.saveDocument(cacheKey, cpiData);
        result.put(seriesId, cpiData);
      } else {
        log.error("No data found for series ID: {}, month: {}, year: {}", seriesId, month, year);
      }
    }

    log.info("Retrieved CPI data for series IDs: {}", result.keySet());
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
    //BLSApiResponse response = objectMapper.readValue(responseJson, BLSApiResponse.class);
    
    
    // Log the deserialized response
    String status = rootNode.get("status").asText();
    int responseTime = rootNode.get("responseTime").asInt();
    List<String> messages = objectMapper.convertValue(rootNode.get("message"), new TypeReference<List<String>>() {});
    
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
   * Extracts the list of data entries for a given year from the BLSApiResponse.
   *
   * @param response the BLSApiResponse object
   * @param year the year for which to extract data
   * @return a list of Data entries for the given year
   */
  private List<DataPoint> extractDataForYear(final BLSApiResponse response, final String year) {
    return response.getResults().getSeries().stream()
        .flatMap(series -> series.getData().stream())
        .filter(data -> data.getYear().equals(year))
        .collect(Collectors.toList());
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
