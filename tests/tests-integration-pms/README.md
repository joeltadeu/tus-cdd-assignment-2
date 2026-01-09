![Java](https://img.shields.io/badge/Java-21-orange)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

# PMS Integration Test

## 📋 Overview

The PMS Integration Tests project provides a complete automated test suite for validating the core services of the
Patient Management System (PMS).
It uses the Karate Framework, a powerful BDD‑style testing tool designed for API automation, data-driven testing, and
parallel execution.
The goal of this project is to ensure that the Patient, Doctor, and Appointment microservices behave correctly across
their full CRUD lifecycle.
These tests run against real service endpoints and validate:

- Resource creation
- Retrieval by ID
- Listing endpoints
- Updates
- Deletions

---

## 🎯 Key Concepts

### Karate Framework

Karate is a test automation framework that combines API testing, data-driven testing, and assertions into a single DSL.

Key features used in this project:

- Feature files written in Gherkin
- Reusable scenarios via call and tags
- Dynamic data generation
- Parallel execution
- Automatic HTML reporting
- Environment-based configuration via karate-config.js

### Service Coverage

This suite validates three PMS microservices:

- Patient Service
- Doctor Service
- Appointment Service

Each service has its own feature file and Java test runner.

### Dynamic Test Data

The project uses Java Faker (via RandomData.java) to generate:

- Random first names
- Random last names
- Random emails

This ensures test isolation and avoids collisions.

---

## 📁 Project Structure

````arduino
tests-integration-pms/
│
├── pom.xml
├── src/test/java/com/pms/integration/
│   ├── AppointmentServiceTest.java
│   ├── DoctorServiceTest.java
│   ├── PatientServiceTest.java
│   ├── TestRunner.java
│   ├── RandomData.java
│   └── features/
│       ├── _config.json
│       ├── Appointment.feature
│       ├── Doctor.feature
│       ├── Patient.feature
│       └── data/
│           ├── appointment_request.json
│           ├── doctor_request.json
│           └── patient_request.json
│
└── src/test/resources/
    ├── karate-config.js
    └── logback-test.xml
````

## ▶️ Main Components

`TestRunner.java`

Runs all feature files in parallel using 5 threads.

Generates:

- Karate HTML report
- Cucumber JSON
- JUnit XML

`PatientServiceTest.java`

Executes only the Patient.feature scenarios tagged with @PatientService.

`DoctorServiceTest.java`

Executes only the Doctor.feature scenarios tagged with @DoctorService.

`AppointmentServiceTest.java`

Executes only the Appointment.feature scenarios tagged with @AppointmentService.

`RandomData.java`

Utility class using Java Faker to generate:

- Random first names
- Random last names
- Random emails

Used by feature files to avoid hardcoded test data.

---

## 🏗️ Architecture Diagram

![Alt text](../../__assets/images/karate-architecture.png?raw=true "Architecture Diagram")

---

## ⚙️ Configuration

`karate-config.js`

Configures:

- Environment selection (`local`, `k8s`, `swarm`)
- Base URLs for each service
- Global timeouts
- Loading `_config.json` with URI paths and default parameters

`logback-test.xml`

Configures logging for:

- Console output
- File logging (target/karate.log)
- Karate framework logs
- PMS integration logs

--- 

## 📦 Dependencies

````xml
<dependency>
    <groupId>io.karatelabs</groupId>
    <artifactId>karate-junit5</artifactId>
    <version>${karate.version}</version>
    <scope>test</scope>
</dependency>
````

Karate version: 1.5.2

---

## 💻 Running the Tests

To execute all tests, use the following Maven command:

````bash
mvn test
````

Run only Patient tests:

````bash
mvn -Dtest=PatientServiceTest test
````

Run only Doctor tests:

````bash
mvn -Dtest=DoctorServiceTest test
````

Run only Appointment tests:

````bash
mvn -Dtest=AppointmentServiceTest test
````

Run with a specific environment:

````bash
mvn test -Dkarate.env=local
mvn test -Dkarate.env=k8s
mvn test -Dkarate.env=swarm
````

---

## 📊 Viewing Reports

After the test run completes, Karate automatically produces a full HTML report under the `target/karate-reports`
directory.
You can open the `karate-summary.html` file in any web browser to review the executed scenarios, request/response logs,
assertion results, and overall test statistics.

![Alt text](../../__assets/images/karate-results.png?raw=true "Karate Test Results Example")

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](https://mit-license.org/) file for details.

---