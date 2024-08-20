package com.bex.cpi_search.model;

import java.util.List;

/** Represents a time series returned from the BLS API. */
public class Series {

  /** The unique identifier for the series. */
  private String seriesID;

  /** The list of data entries associated with the series. */
  private List<DataPoint> data;

  /**
   * Gets the unique identifier for the series.
   *
   * @return the series ID
   */
  public String getSeriesID() {
    return seriesID;
  }

  /**
   * Sets the unique identifier for the series.
   *
   * @param seriesIDValue the series ID to set
   */
  public void setSeriesID(final String seriesIDValue) {
    this.seriesID = seriesIDValue;
  }

  /**
   * Gets the list of data entries associated with the series.
   *
   * @return the list of data entries
   */
  public List<DataPoint> getData() {
    return data;
  }

  /**
   * Sets the list of data entries associated with the series.
   *
   * @param dataValue the list of data entries to set
   */
  public void setData(final List<DataPoint> dataValue) {
    this.data = dataValue;
  }

  @Override
  public String toString() {
    return "Series{" + "seriesID='" + seriesID + '\'' + ", data=" + data + '}';
  }
}
