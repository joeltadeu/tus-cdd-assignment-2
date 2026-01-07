package com.pms.performance.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

import com.pms.performance.config.SimulationConfig;
import com.pms.performance.scenario.PmsEndToEndScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.time.Duration;
import java.util.*;

public class PmsLoadSimulation extends Simulation {

  HttpProtocolBuilder httpProtocol = http.acceptHeader("application/json");

  {
    List<ClosedInjectionStep> steps = new ArrayList<>();

    steps.add(rampConcurrentUsers(0).to(SimulationConfig.getUsers()).during(60));
    steps.add(
        constantConcurrentUsers(SimulationConfig.getUsers())
            .during(Duration.ofMinutes(SimulationConfig.getDurationMinutes())));

    setUp(PmsEndToEndScenario.build().injectClosed(steps))
        .protocols(httpProtocol)
        .assertions(
            global().failedRequests().percent().is(0.0),
            global().responseTime().percentile(95).lt(800));
  }
}
