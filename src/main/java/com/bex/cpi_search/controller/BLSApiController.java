package com.bex.cpi_search.controller;

import com.bex.cpi_search.model.BLSApiRequest;
import com.bex.cpi_search.service.BLSApiService;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling requests related to BLS API data. Provides endpoints for displaying the
 * request form and processing form submissions.
 */
@Controller
public class BLSApiController {

  /** Service for interacting with the BLS API. */
  private final BLSApiService blsApiService;

  /** List of available series IDs for selection. */
  private static final List<String> SERIES_IDS =
      Arrays.asList(
          "LAUCN040010000000005",
          "LAUCN040010000000006",
          "OEUN000000056--5747213213",
          "LUU0202891000");

  /**
   * Constructor for BLSApiController.
   *
   * @param providedBlsApiService service for BLS API interaction
   */
  public BLSApiController(final BLSApiService providedBlsApiService) {
    this.blsApiService = providedBlsApiService;
  }

  /**
   * Displays the form for requesting BLS data.
   *
   * @param model model to pass data to the view
   * @return the name of the view template for the request form
   */
  @GetMapping("/request-data")
  public String showForm(final Model model) {
    model.addAttribute("seriesIds", SERIES_IDS);
    model.addAttribute("years", getYears());
    model.addAttribute("months", getMonths());
    return "request-form";
  }

  /**
   * Handles the form submission for requesting BLS data.
   *
   * @param seriesIds list of selected series IDs
   * @param startYear starting year for the data request
   * @param endYear ending year for the data request
   * @param startMonth starting month for the data request
   * @param endMonth ending month for the data request
   * @param model model to pass data to the view
   * @return the name of the view template to display the response
   */
  @PostMapping("/request-data")
  public String handleFormSubmission(
      @RequestParam final List<String> seriesIds,
      @RequestParam final String startYear,
      @RequestParam final String endYear,
      @RequestParam final String startMonth,
      @RequestParam final String endMonth,
      final Model model) {

    BLSApiRequest request = new BLSApiRequest();
    request.setSeriesId(seriesIds);
    request.setStartYear(startYear);
    request.setEndYear(endYear);

    // Call the BLS API service to fetch data
    String response = blsApiService.fetchData(request);

    model.addAttribute("response", response);
    return "response";
  }

  /**
   * Provides a list of years for selection.
   *
   * @return list of years
   */
  private List<String> getYears() {
    return Arrays.asList("2020", "2021", "2022", "2023", "2024");
  }

  /**
   * Provides a list of months for selection.
   *
   * @return list of months
   */
  private List<String> getMonths() {
    return Arrays.asList(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December");
  }
}
