package com.bex.cpi_search.model;

import java.util.List;
import java.util.logging.Logger;

/**
 * Represents the results returned by the BLS (Bureau of Labor Statistics) API. This class contains
 * a list of {@link Series} objects, which encapsulate the data series retrieved from the API.
 */
public final class Results {

  /** Logger for logging messages in this class. */
  private static final Logger LOGGER = Logger.getLogger(Results.class.getName());

  /** A list of {@link Series} objects that contain the data series retrieved from the API. */
  private List<Series> series;

  /**
   * Gets the list of data series returned by the API.
   *
   * @return the list of {@link Series} objects
   */
  public List<Series> getSeries() {
    return series;
  }

  /**
   * Sets the list of data series returned by the API.
   *
   * @param seriesValue the list of {@link Series} objects to set
   */
  public void setSeries(final List<Series> seriesValue) {
    LOGGER.info("Setting series to: " + seriesValue);
    this.series = seriesValue;
  }

  @Override
  public String toString() {
    return "Results{"
        + "series="
        + series
        + '}';
  }
}
