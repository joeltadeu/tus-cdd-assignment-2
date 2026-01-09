package com.pms.performance.chain;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import com.pms.performance.payload.DoctorPayloadBuilder;
import com.pms.performance.config.SimulationConfig;
import io.gatling.javaapi.core.*;

public final class DoctorChains {

  private DoctorChains() {}

  public static ChainBuilder createDoctor() {
    return exec(session -> session.set("doctorPayload", DoctorPayloadBuilder.build()))
        .exec(
            http("Create doctor")
                .post(SimulationConfig.getDoctorUrl() + "/v1/doctors")
                .header("Content-Type", "application/json")
                .body(StringBody("#{doctorPayload}"))
                .asJson()
                .check(status().is(201))
                .check(jsonPath("$.id").saveAs("doctorId")))
        .exitHereIfFailed()
        .pause(1);
  }

  public static ChainBuilder getDoctorById() {
    return exec(http("Get doctor by id")
            .get(SimulationConfig.getDoctorUrl() + "/v1/doctors/#{doctorId}")
            .check(status().is(200)))
        .pause(1);
  }

  public static ChainBuilder getAllDoctors() {
    return exec(http("Get all doctors")
            .get(SimulationConfig.getDoctorUrl() + "/v1/doctors")
            .check(status().is(200)))
        .pause(1);
  }
}
