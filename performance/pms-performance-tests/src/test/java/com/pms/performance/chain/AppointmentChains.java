package com.pms.performance.chain;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import com.pms.performance.config.SimulationConfig;
import com.pms.performance.payload.AppointmentPayloadBuilder;
import io.gatling.javaapi.core.*;

public final class AppointmentChains {

  private AppointmentChains() {}

  public static ChainBuilder createAppointment() {
    return exec(session -> {
          long patientId = session.getLong("patientId");
          long doctorId = session.getLong("doctorId");

          String payload = AppointmentPayloadBuilder.build(patientId, doctorId);

          return session.set("appointmentPayload", payload);
        })
        .exec(
            http("Create appointment")
                .post(SimulationConfig.getAppointmentUrl() + "/v1/appointments")
                .header("Content-Type", "application/json")
                .body(StringBody("#{appointmentPayload}"))
                .asJson()
                .check(status().is(201))
                .check(jsonPath("$.id").saveAs("appointmentId")))
        .exitHereIfFailed()
        .pause(1);
  }

  public static ChainBuilder getAppointmentById() {
    return exec(http("Get appointment by id")
            .get(SimulationConfig.getAppointmentUrl() + "/v1/appointments/#{appointmentId}")
            .check(status().is(200)))
        .pause(1);
  }
}
