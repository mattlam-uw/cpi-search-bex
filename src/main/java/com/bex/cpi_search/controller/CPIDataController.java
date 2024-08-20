package com.bex.cpi_search.controller;

import com.bex.cpi_search.model.CPIData;
import com.bex.cpi_search.service.BLSApiService;
import com.bex.cpi_search.service.CPIDataService;
import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Controller for handling requests related to CPI (Consumer Price Index) data. */
@Controller
public class CPIDataController {

  /** Service for interacting with CPI data. */
  private final CPIDataService cpiDataService;

  private static final Logger log = LoggerFactory.getLogger(BLSApiService.class);

  /** List of available series IDs for selection. */
  private static final List<String> SERIES_IDS =
      List.of(
          "LAUCN040010000000005",
          "LAUCN040010000000006",
          "OEUN000000056--5747213213",
          "LUU0202891000");

  /**
   * Constructor for CPIDataController.
   *
   * @param providedCpiDataService service for CPI data interaction
   */
  public CPIDataController(final CPIDataService providedCpiDataService) {
    this.cpiDataService = providedCpiDataService;
  }

  /**
   * Displays the form for requesting CPI data and optionally the results.
   *
   * @param seriesIds selected series IDs
   * @param year selected year
   * @param month selected month
   * @param model model to pass data to the view
   * @return the name of the view template for the request form and results
   */
  @GetMapping("/request-data")
  public String showForm(final Model model) {
    model.addAttribute("seriesIds", SERIES_IDS);
    model.addAttribute("months", getMonths());
    model.addAttribute("years", getYears());
    return "request-response";
  }

  /**
   * Handles the form submission for requesting CPI data.
   *
   * @param seriesIds selected series IDs
   * @param year selected year
   * @param month selected month
   * @param model model to pass data to the view
   * @return the name of the view template to display the response
   */
  @PostMapping("/request-data")
  public String handleFormSubmission(
      @RequestParam List<String> seriesIds,
      @RequestParam String year,
      @RequestParam String month,
      final Model model)
      throws IOException {

    try {
      Map<String, CPIData> response = cpiDataService.getCPIData(year, month, seriesIds);

      model.addAttribute("response", response);
      model.addAttribute("seriesIds", SERIES_IDS);
      model.addAttribute("years", getYears());
      model.addAttribute("months", getMonths());
      model.addAttribute("selectedYear", year);
      model.addAttribute("selectedMonth", month);
      return "request-response";
    } catch (RuntimeException e) {
      // Log the error and add an attribute to the model to display the error message

      log.error("An error occurred while fetching CPI data.", e);

      model.addAttribute(
          "errorMessage", "An error occurred while fetching CPI data." + e.getMessage());
      return "error"; // Name of the error view template
    }
  }

  /**
   * Provides a list of years for selection.
   *
   * @return list of years
   */
  private List<String> getYears() {
    return Stream.iterate(YearMonth.now().getYear(), year -> year - 1)
        .limit(20)
        .map(String::valueOf)
        .collect(Collectors.toList());
  }

  /**
   * Provides a list of months for selection.
   *
   * @return list of months
   */
  private List<String> getMonths() {
    return Stream.of(
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
            "December")
        .collect(Collectors.toList());
  }
}
