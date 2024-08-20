package com.bex.cpi_search.model;

import java.util.List;

/** Represents a data entry returned from the BLS API. */
public final class Data {

  /** The year for the data entry. */
  private String year;

  /** The period for the data entry (e.g., month or quarter). */
  private String period;

  /** The name of the period (e.g., "January" or "Q1"). */
  private String periodName;

  /** Indicates whether the data is the latest available. */
  private String latest;

  /** The value of the data entry. */
  private String value;

  /** A list of footnotes associated with the data entry. */
  private List<Footnote> footnotes;

  /**
   * Gets the year for the data entry.
   *
   * @return the year
   */
  public String getYear() {
    return year;
  }

  /**
   * Sets the year for the data entry.
   *
   * @param yearValue the year to set
   */
  public void setYear(final String yearValue) {
    this.year = yearValue;
  }

  /**
   * Gets the period for the data entry.
   *
   * @return the period
   */
  public String getPeriod() {
    return period;
  }

  /**
   * Sets the period for the data entry.
   *
   * @param periodValue the period to set
   */
  public void setPeriod(final String periodValue) {
    this.period = periodValue;
  }

  /**
   * Gets the name of the period for the data entry.
   *
   * @return the period name
   */
  public String getPeriodName() {
    return periodName;
  }

  /**
   * Sets the name of the period for the data entry.
   *
   * @param periodNameValue the period name to set
   */
  public void setPeriodName(final String periodNameValue) {
    this.periodName = periodNameValue;
  }

  /**
   * Checks if the data entry is the latest available.
   *
   * @return the latest indicator
   */
  public String getLatest() {
    return latest;
  }

  /**
   * Sets whether the data entry is the latest available.
   *
   * @param latestValue the latest indicator to set
   */
  public void setLatest(final String latestValue) {
    this.latest = latestValue;
  }

  /**
   * Gets the value of the data entry.
   *
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value of the data entry.
   *
   * @param providedValue the value to set
   */
  public void setValue(final String providedValue) {
    this.value = providedValue;
  }

  /**
   * Gets the list of footnotes associated with the data entry.
   *
   * @return the list of footnotes
   */
  public List<Footnote> getFootnotes() {
    return footnotes;
  }

  /**
   * Sets the list of footnotes associated with the data entry.
   *
   * @param footnotesList the list of footnotes to set
   */
  public void setFootnotes(final List<Footnote> footnotesList) {
    this.footnotes = footnotesList;
  }
}
