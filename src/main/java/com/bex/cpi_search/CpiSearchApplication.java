package com.bex.cpi_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Spring Boot application. This class contains the main method that
 * launches the application.
 */
@SpringBootApplication
public class CpiSearchApplication {

  /**
   * The main method to start the Spring Boot application.
   *
   * @param args command-line arguments passed during application startup
   */
  public static void main(final String[] args) {
    SpringApplication.run(CpiSearchApplication.class, args);
  }
}
