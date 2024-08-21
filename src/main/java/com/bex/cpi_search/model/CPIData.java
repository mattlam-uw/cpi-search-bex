package com.bex.cpi_search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the CPI (Consumer Price Index) data to be returned to the front end and stored in
 * Redis.
 */
public class CPIData {

  @JsonProperty("seriesId")
  private String seriesId;

  @JsonProperty("year")
  private String year;

  @JsonProperty("month")
  private String month;

  @JsonProperty("cpiValue")
  private String cpiValue;

  @JsonProperty("notes")
  private List<String> notes;

  // Getters and setters

  public String getSeriesId() {
    return seriesId;
  }

  public void setSeriesId(final String seriesId) {
    this.seriesId = seriesId;
  }

  public String getYear() {
    return year;
  }

  public void setYear(final String year) {
    this.year = year;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(final String month) {
    this.month = month;
  }

  public String getCPIValue() {
    return cpiValue;
  }

  public void setCPIValue(final String cpiValue) {
    this.cpiValue = cpiValue;
  }

  public List<String> getNotes() {
    return this.notes;
  }

  public void setNotes(final List<String> notes) {
    this.notes = notes;
  }
}
