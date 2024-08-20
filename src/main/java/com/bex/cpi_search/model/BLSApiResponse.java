package com.bex.cpi_search.model;

import java.util.List;

/**
 * Represents the response from the BLS (Bureau of Labor Statistics) API. This class contains the
 * status of the response, the time taken to respond, any messages returned, and the results of the
 * API call.
 */
public final class BLSApiResponse {

  /** The status of the API response, typically indicating success or failure. */
  private String status;

  /** The time taken by the API to respond, in milliseconds. */
  private int responseTime;

  /** Any messages returned by the API, usually containing warnings or additional information. */
  private List<String> message;

  /** The results returned by the API, encapsulated in a {@link Results} object. */
  private Results results;

  /**
   * Gets the status of the API response.
   *
   * @return the status of the API response
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the status of the API response.
   *
   * @param statusValue the status to set, typically indicating success or failure
   */
  public void setStatus(final String statusValue) {
    this.status = statusValue;
  }

  /**
   * Gets the time taken by the API to respond.
   *
   * @return the response time in milliseconds
   */
  public int getResponseTime() {
    return responseTime;
  }

  /**
   * Sets the time taken by the API to respond.
   *
   * @param responseTimeValue the response time to set, in milliseconds
   */
  public void setResponseTime(final int responseTimeValue) {
    this.responseTime = responseTimeValue;
  }

  /**
   * Gets any messages returned by the API.
   *
   * @return a list of messages, usually containing warnings or additional information
   */
  public List<String> getMessage() {
    return message;
  }

  /**
   * Sets the messages returned by the API.
   *
   * @param messageValue the list of messages to set, usually containing warnings or additional
   *     information
   */
  public void setMessage(final List<String> messageValue) {
    this.message = messageValue;
  }

  /**
   * Gets the results returned by the API.
   *
   * @return the results of the API call, encapsulated in a {@link Results} object
   */
  public Results getResults() {
    return results;
  }

  /**
   * Sets the results returned by the API.
   *
   * @param resultsValue the results to set, encapsulated in a {@link Results} object
   */
  public void setResults(final Results resultsValue) {
    this.results = resultsValue;
  }
}
