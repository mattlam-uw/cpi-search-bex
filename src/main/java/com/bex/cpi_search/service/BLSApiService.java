package com.bex.cpi_search.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/** Service class for interacting with the BLS API. */
@Service
public class BLSApiService {

  /**
   * The RestTemplate used for making HTTP requests to the BLS API.
   *
   * <p>RestTemplate is a synchronous client to perform HTTP requests, exposing a simple, template
   * method API over underlying HTTP client libraries. It is used in this service to interact with
   * the BLS API by sending POST requests with a JSON payload and receiving the response.
   */
  private final RestTemplate restTemplate;

  /**
   * Constructs a BLSApiService with the specified RestTemplate.
   *
   * @param template the RestTemplate to be used for API calls
   */
  public BLSApiService(final RestTemplate template) {
    this.restTemplate = template;
  }

  /**
   * Fetches data from the BLS API.
   *
   * @param seriesIds an array of series IDs to fetch data for
   * @param startYear the starting year for the data
   * @param endYear the ending year for the data
   * @param catalog whether to include catalog information
   * @param calculations whether to include calculations
   * @param annualAverage whether to include annual average
   * @param aspects whether to include aspects
   * @return the response body from the BLS API as a String
   */
  public String fetchData(
      final String[] seriesIds,
      final String startYear,
      final String endYear,
      final boolean catalog,
      final boolean calculations,
      final boolean annualAverage,
      final boolean aspects) {

    final String url = "https://api.bls.gov/publicAPI/v2/timeseries/data/";

    // Create request payload
    final Map<String, Object> payload = new HashMap<>();
    payload.put("seriesid", seriesIds);
    payload.put("startyear", startYear);
    payload.put("endyear", endYear);
    payload.put("catalog", catalog);
    payload.put("calculations", calculations);
    payload.put("annualaverage", annualAverage);
    payload.put("aspects", aspects);

    // Create request entity
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    final HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

    // Make the POST request
    final ResponseEntity<String> response =
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

    return response.getBody();
  }
}
