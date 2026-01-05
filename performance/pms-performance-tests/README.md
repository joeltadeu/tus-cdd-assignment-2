![Java](https://img.shields.io/badge/Java-21-orange)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

# PMS Performance Test

## 📋 Overview

This project is a performance and load testing suite built with the Gatling Framework.
It simulates a realistic end-to-end workflow across three independent microservices:

- Doctor Service
- Patient Service
- Appointment Service

The main goal is to validate:
- System behavior under concurrent load
- End-to-end interaction between services
- Response time SLAs
- Error rates under sustained traffic

The simulation executes a full business flow:
- Create and retrieve doctors
- Create and retrieve patients
- Create and retrieve appointments linking doctors and patients

At the end of each execution, Gatling generates an HTML performance report with detailed metrics such as:
- Response time distributions
- Requests per second
- Error rates
- Percentiles (P50, P75, P95, P99)

---

## 🎯 Key Concepts

- Framework: Gatling (Java DSL)
- Test Type: Closed workload model (constant concurrent users)
- Assertions:
    - 0% failed requests
    - 95th percentile < 800 ms
- Execution Model:
    - Ramp-up phase
    - Constant load phase

---

## 📁 Project Structure

````arduino
src
└── test
├── java
│   └── com.pms.performance
│       ├── simulation
│       │   └── PmsLoadSimulation.java
│       ├── scenario
│       │   └── PmsEndToEndScenario.java
│       ├── chains
│       │   ├── DoctorChains.java
│       │   ├── PatientChains.java
│       │   └── AppointmentChains.java
│       └── config
│           └── SimulationConfig.java
└── resources
├── application.properties
└── gatling.conf
````

---

## ▶️ Simulation Entry Point

`PmsLoadSimulation`

This is the main simulation class executed by Gatling.

Responsibilities:
- Define the HTTP protocol
- Configure user injection
- Apply global assertions
- Wire the scenario to the execution model

Load Profile
- Ramp-up: 0 → N users in 60 seconds
- Steady state: N concurrent users for X minutes

````java
public class PmsLoadSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http.acceptHeader("application/json");
    {
        setUp(PmsEndToEndScenario.build().injectClosed(steps))
            .protocols(httpProtocol)
            .assertions(
                global().failedRequests().percent().is(0.0),
                global().responseTime().percentile(95).lt(800));
    }
}
````

---

## 🔄 End-to-End Scenario

`PmsEndToEndScenario`
This class defines the complete user journey across all three services.

Execution order:

1. Create Doctor
2. Get Doctor by ID
3. Get All Doctors
4. Create Patient
5. Get Patient by ID
6. Get All Patients
7. Create Appointment
8. Get Appointment by ID

Each step depends on data created in previous steps, stored in the Gatling session.

---

## 🔗 Chains and Endpoint Calls

Each microservice has its own Chain class, responsible for:
- Building request payloads
- Executing HTTP calls
- Validating responses
- Extracting IDs for later use

List of chain classes:
- `DoctorChains`: Handles doctor-related requests (create, get by ID, get all)
- `PatientChains`: Handles patient-related requests (create, get by ID, get all)
- `AppointmentChains`: Handles appointment-related requests (create, get by ID)

--- 

## 🏗️ Architecture Diagram

![Alt text](../../__assets/images/gatling-architecture.png?raw=true "Project Architecture Diagram")


## ⚙️ Configuration

`SimulationConfig`

Configuration values are resolved in the following order:
1. JVM system properties (-Dkey=value)
2. application.properties
3. Default values

### Default Configuration (application.properties)

```properties
patientUrl=http://localhost:9081
doctorUrl=http://localhost:9082
appointmentUrl=http://localhost:9083
users=2
durationMinutes=2
```

### Configurable Parameters
- `patientUrl`: Base URL for Patient Service
- `doctorUrl`: Base URL for Doctor Service
- `appointmentUrl`: Base URL for Appointment Service
- `users`: Number of concurrent users to simulate
- `durationMinutes`: Duration of the steady-state load phase

---

## 📦 Dependencies
Key libraries required to run Gatling tests:

````xml
<dependency>
    <groupId>io.gatling.highcharts</groupId>
    <artifactId>gatling-charts-highcharts</artifactId>
    <version>${gatling.version}</version>
</dependency>

<dependency>
    <groupId>io.gatling</groupId>
    <artifactId>gatling-app</artifactId>
    <version>${gatling.version}</version>
</dependency>

<dependency>
    <groupId>io.gatling</groupId>
    <artifactId>gatling-recorder</artifactId>
    <version>${gatling.version}</version>
</dependency>
````
---

## 💻 Running the Tests
To execute the performance tests, use the following Maven command:

````bash
mvn gatling:test -Dgatling.simulationClass=com.pms.performance.simulation.PmsLoadSimulation \
-DpatientUrl=http://localhost:9081 \
-DdoctorUrl=http://localhost:9082 \
-DappointmentUrl=http://localhost:9083 \
-Dusers=5 \
-DdurationMinutes=10
````

---

## 📊 Viewing Reports
After the test execution, Gatling generates an HTML report located in the `target/gatling` directory.
Open the `index.html` file in a web browser to view detailed performance metrics and charts.

Report Includes
- Global request statistics
- Response time percentiles
- Error distribution
- Requests per second
- Individual endpoint metrics

![Alt text](../../__assets/images/gatling-results.png?raw=true "Gatling Test Results Example")

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](https://mit-license.org/) file for details.

---



