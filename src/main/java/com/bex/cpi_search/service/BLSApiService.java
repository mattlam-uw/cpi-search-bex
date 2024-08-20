package com.bex.cpi_search.service;

import com.bex.cpi_search.model.BLSApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  /** The RestTemplate used to make HTTP requests to the BLS API. */
  private final RestTemplate restTemplate;

  private static final Logger log = LoggerFactory.getLogger(BLSApiService.class);

  /**
   * Constructs a BLSApiService with the specified RestTemplate.
   *
   * @param providedRestTemplate the RestTemplate to be used for API calls
   */
  public BLSApiService(final RestTemplate providedRestTemplate) {
    this.restTemplate = providedRestTemplate;
  }

  /**
   * Fetches data from the BLS API.
   *
   * @param request the BLSApiRequest object containing request parameters
   * @return the response body from the BLS API as a String
   */
  public String fetchData(final BLSApiRequest request) {
    final String url = "https://api.bls.gov/publicAPI/v2/timeseries/data/";

    // Create request entity
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    final HttpEntity<BLSApiRequest> requestEntity = new HttpEntity<>(request, headers);

    // Log request details
    log.info("Sending request to BLS API: URL = {}, Request = {}", url, request);

    // Make the POST request
    final ResponseEntity<String> response =
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

    // Log response details
    log.info(
        "Received response from BLS API: Status = {}, Body = {}",
        response.getStatusCode(),
        response.getBody());

    // Check for errors and handle them if needed
    if (response.getStatusCode().is2xxSuccessful()) {
      return response.getBody();
    } else {
      // Handle errors as needed
      throw new RuntimeException(
          "Failed to fetch data from BLS API, Status Code: " + response.getStatusCode());
    }
  }
}
