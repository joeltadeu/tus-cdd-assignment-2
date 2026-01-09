![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Appointment Microservice

## 📋 Overview

The Appointment Service API handles the scheduling and tracking of medical consultations. It integrates with the Doctor and Patient services to validate participants, enabling the creation of appointments, retrieval of consultation details, and monitoring of appointment statuses.

---

## 🏗️ Architecture

![Alt text](../../__assets/images/appointment-architecture.png?raw=true "Architecture Diagram")


---

## ️🗄️ Database

![Alt text](../../__assets/images/appointment-der.png?raw=true "Entity Relationship Diagram")

---

## 📚 API Endpoints

### Retrieve Appointment by ID
- **Endpoint:** `GET /api/v1/appointments/{id}`
- **Description:** Fetches details of a specific appointment by its ID.
  - **Response:** `200 OK` with a JSON object of the appointment.

Response Example:
```json
{
  "id": 1,
  "doctorId": 101,
  "patientId": 202,
  "appointmentDate": "2024-07-15T10:00:00",
  "status": "SCHEDULED"
}
```

### Create New Appointment
- **Endpoint:** `POST /api/v1/appointments`
- **Description:** Schedules a new appointment between a doctor and a patient.
- **Response:** `201 Created` with a JSON object of the created appointment.
   
Request Example:
```json
{
  "title": "Knee Pain Consultation",
  "type": "CONSULTATION",
  "startTime": "2025-10-20T14:30:00",
  "patientId": 6,
  "doctorId": 5,
  "description": "Patient is experiencing persistent knee pain after running. Initial consultation to diagnose the issue."
}
```

Response Example:
```json
{
  "id": 10,
  "patient": {
    "id": 6,
    "firstName": "Abbie",
    "lastName": "Erdman",
    "email": "abbie.erdman@gmail.com"
  },
  "doctor": {
    "id": 5,
    "firstName": "Suzanne",
    "lastName": "Brekke",
    "title": "Dr.",
    "speciality": "Primary Care"
  },
  "startTime": "2025-10-20T14:30:00",
  "endTime": "2025-10-20T15:30:00",
  "duration": 60,
  "description": "Patient is experiencing persistent knee pain after running. Initial consultation to diagnose the issue.",
  "type": "CONSULTATION",
  "status": "SCHEDULED"
}
```

---

## 📘 Documentation & Testing

### OpenAPI / Swagger
Once service is running, you can access the interactive API documentation:

http://localhost:9083/swagger-ui.html

![Alt text](../../__assets/images/appointment-openapi-documentation.png?raw=true "OpenAPI Documentation Example")

### Postman Collection

A **Postman collection** is provided to test all APIs.
- Location: `/documentation/postman/PMS.postman_collection.json`

---

## 🔨 Build Project & Running Locally

To build the entire project using Maven:

```bash
mvn clean install
```

### Running Locally
To run a microservice locally using the command:

```bashbash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Running as a Container

Microservice includes a **Dockerfile** to build a lightweight, secure container image using [Distroless](https://github.com/GoogleContainerTools/distroless).

```dockerfile
FROM gcr.io/distroless/java21-debian13

ADD target/appointment-service.jar appointment-service.jar

EXPOSE 9083

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/appointment-service.jar"]
```

Execute the command below to run the container individually (requires database setup):

```bash
docker run -e SPRING_PROFILES_ACTIVE=local -p 9083:9083 appointment-service
```
---

## 🛠️ Stack

- Java 21
- Spring Boot 3.5.9
- Spring Data JPA
- Spring Cloud OpenFeign
- OpenAPI 3.1.0
- MariaDB

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](https://mit-license.org/) file for details.

---