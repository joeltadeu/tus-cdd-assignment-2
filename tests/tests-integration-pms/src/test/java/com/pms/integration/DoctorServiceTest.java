package com.pms.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;

/** Test Runner for Doctor Service Executes only Doctor-related test scenarios */
public class DoctorServiceTest {

  @Test
  public void testDoctorService() {
    Results results =
        Runner.path("classpath:com/pms/integration/features/Doctor.feature")
            .tags("@DoctorService")
            .outputCucumberJson(true)
            .outputJunitXml(true)
            .parallel(1);

    assertEquals(0, results.getFailCount(), results.getErrorMessages());
  }
}
