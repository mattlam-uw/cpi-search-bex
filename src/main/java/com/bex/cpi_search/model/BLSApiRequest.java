package com.bex.cpi_search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Represents a request to the BLS API for timeseries data. */
public class BLSApiRequest {

  /** A list of series IDs to request data for. */
  @JsonProperty("seriesid")
  private List<String> seriesId;

  /** The starting year for the data request. */
  @JsonProperty("startyear")
  private String startYear;

  /** The ending year for the data request. */
  @JsonProperty("endyear")
  private String endYear;

  /** Indicates whether to include catalog information in the response. */
  @JsonProperty("catalog")
  private boolean catalog;

  /** Indicates whether to include calculations in the response. */
  @JsonProperty("calculations")
  private boolean calculations;

  /** Indicates whether to include annual average data in the response. */
  @JsonProperty("annualaverage")
  private boolean annualAverage;

  /** Indicates whether to include aspects data in the response. */
  @JsonProperty("aspects")
  private boolean aspects;

  /**
   * Gets the list of series IDs.
   *
   * @return the list of series IDs
   */
  public List<String> getSeriesId() {
    return seriesId;
  }

  /**
   * Sets the list of series IDs.
   *
   * @param seriesIdValue the list of series IDs to set
   */
  public void setSeriesId(final List<String> seriesIdValue) {
    this.seriesId = seriesIdValue;
  }

  /**
   * Gets the starting year for the data request.
   *
   * @return the starting year
   */
  public String getStartYear() {
    return startYear;
  }

  /**
   * Sets the starting year for the data request.
   *
   * @param startYearValue the starting year to set
   */
  public void setStartYear(final String startYearValue) {
    this.startYear = startYearValue;
  }

  /**
   * Gets the ending year for the data request.
   *
   * @return the ending year
   */
  public String getEndYear() {
    return endYear;
  }

  /**
   * Sets the ending year for the data request.
   *
   * @param endYearValue the ending year to set
   */
  public void setEndYear(final String endYearValue) {
    this.endYear = endYearValue;
  }

  /**
   * Checks if catalog information should be included in the response.
   *
   * @return true if catalog information is included, false otherwise
   */
  public boolean isCatalog() {
    return catalog;
  }

  /**
   * Sets whether catalog information should be included in the response.
   *
   * @param catalogValue true to include catalog information, false otherwise
   */
  public void setCatalog(final boolean catalogValue) {
    this.catalog = catalogValue;
  }

  /**
   * Checks if calculations should be included in the response.
   *
   * @return true if calculations are included, false otherwise
   */
  public boolean isCalculations() {
    return calculations;
  }

  /**
   * Sets whether calculations should be included in the response.
   *
   * @param calculationsValues true to include calculations, false otherwise
   */
  public void setCalculations(final boolean calculationsValues) {
    this.calculations = calculationsValues;
  }

  /**
   * Checks if annual average data should be included in the response.
   *
   * @return true if annual average data is included, false otherwise
   */
  public boolean isAnnualAverage() {
    return annualAverage;
  }

  /**
   * Sets whether annual average data should be included in the response.
   *
   * @param annualAverageValue true to include annual average data, false otherwise
   */
  public void setAnnualAverage(final boolean annualAverageValue) {
    this.annualAverage = annualAverageValue;
  }

  /**
   * Checks if aspects data should be included in the response.
   *
   * @return true if aspects data is included, false otherwise
   */
  public boolean isAspects() {
    return aspects;
  }

  /**
   * Sets whether aspects data should be included in the response.
   *
   * @param aspectsValue true to include aspects data, false otherwise
   */
  public void setAspects(final boolean aspectsValue) {
    this.aspects = aspectsValue;
  }
}
