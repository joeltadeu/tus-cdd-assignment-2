package com.pms.performance.scenario;

import static io.gatling.javaapi.core.CoreDsl.*;

import com.pms.performance.chain.AppointmentChains;
import com.pms.performance.chain.DoctorChains;
import com.pms.performance.chain.PatientChains;
import io.gatling.javaapi.core.*;

public final class PmsEndToEndScenario {

  private PmsEndToEndScenario() {}

  public static ScenarioBuilder build() {
    return scenario("PMS End-to-End Scenario")
        .exec(DoctorChains.createDoctor())
        .exec(DoctorChains.getDoctorById())
        .exec(DoctorChains.getAllDoctors())
        .exec(PatientChains.createPatient())
        .exec(PatientChains.getPatientById())
        .exec(PatientChains.getAllPatients())
        .exec(AppointmentChains.createAppointment())
        .exec(AppointmentChains.getAppointmentById());
  }
}
