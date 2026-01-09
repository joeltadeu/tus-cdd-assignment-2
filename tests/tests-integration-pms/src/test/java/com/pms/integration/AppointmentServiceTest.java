package com.pms.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;

/** Test Runner for Appointment Service Executes only Appointment-related test scenarios */
public class AppointmentServiceTest {

  @Test
  public void testAppointmentService() {
    Results results =
        Runner.path("classpath:com/pms/integration/features/Appointment.feature")
            .tags("@AppointmentService")
            .outputCucumberJson(true)
            .outputJunitXml(true)
            .parallel(1);

    assertEquals(0, results.getFailCount(), results.getErrorMessages());
  }
}
