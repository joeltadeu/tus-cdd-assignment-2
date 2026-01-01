package com.pms.performance.chain;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import com.pms.performance.payload.PatientPayloadBuilder;
import com.pms.performance.config.SimulationConfig;
import io.gatling.javaapi.core.*;

public final class PatientChains {

  private PatientChains() {}

  public static ChainBuilder createPatient() {
    return exec(session -> session.set("patientPayload", PatientPayloadBuilder.build()))
        .exec(
            http("Create patient")
                .post(SimulationConfig.getPatientUrl() + "/v1/patients")
                .header("Content-Type", "application/json")
                .body(StringBody("#{patientPayload}"))
                .asJson()
                .check(status().is(201))
                .check(jsonPath("$.id").saveAs("patientId")))
        .exitHereIfFailed()
        .pause(1);
  }

  public static ChainBuilder getPatientById() {
    return exec(http("Get patient by id")
            .get(SimulationConfig.getPatientUrl() + "/v1/patients/#{patientId}")
            .check(status().is(200)))
        .pause(1);
  }

  public static ChainBuilder getAllPatients() {
    return exec(http("Get all patients")
            .get(SimulationConfig.getPatientUrl() + "/v1/patients")
            .check(status().is(200)))
        .pause(1);
  }
}
