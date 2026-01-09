package com.pms.integration;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Main Test Runner for Karate Integration Tests This class executes all feature files and generates
 * HTML reports
 */
public class TestRunner {

  @Test
  public void testParallel() {
    // Run all tests in parallel with 5 threads
    // HTML report is automatically generated in target/karate-reports
    Results results =
        Runner.path("classpath:com/pms/integration/features")
            .outputCucumberJson(true)
            .outputJunitXml(true)
            .parallel(5);

    // Assert that all tests passed
    assertEquals(0, results.getFailCount(), results.getErrorMessages());
  }
}
