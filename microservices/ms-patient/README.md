![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Patient Microservice

## 📋 Overview

The Patient Service API manages comprehensive patient records, handling personal and demographic information. It provides endpoints to register new patients, retrieve individual profiles, update existing records, and manage patient data securely.

---

## 🏗️ Architecture

![Alt text](../../__assets/images/patient-architecture.png?raw=true "Architecture Diagram")


---

## ️🗄️ Database

![Alt text](../../__assets/images/patient-der.png?raw=true "Entity Relationship Diagram")

---

## 📚 API Endpoints

### List All Patients
- **Endpoint:** `GET /api/v1/patients`
- **Description:** Retrieves a list of all registered patients.
- **Response:** `200 OK` with a JSON array of patient objects.

Response Example:
```json
{
  "content": [
    {
      "id": 6,
      "firstName": "Abbie",
      "lastName": "Erdman",
      "email": "abbie.erdman@gmail.com",
      "address": "3456 Fox Street, California, 32121",
      "dateOfBirth": "1980-09-30"
    },
    {
      "id": 5,
      "firstName": "Hollis",
      "lastName": "Donnelly",
      "email": "hollis.donnelly@gmail.com",
      "address": "8369 Maple Road, New York, 10088",
      "dateOfBirth": "1978-10-14"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 2,
  "totalPages": 1,
  "first": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 2,
  "empty": false
}
```

### Retrieve Patient by ID
- **Endpoint:** `GET /api/v1/patients/{id}`
- **Description:** Fetches details of a specific patient by their ID.
- **Response:** `200 OK` with a JSON object of the patient.

Response Example:
```json
{
  "id": 6,
  "firstName": "Abbie",
  "lastName": "Erdman",
  "email": "abbie.erdman@gmail.com",
  "address": "2625 Main Street, Miami, 33177",
  "dateOfBirth": "1998-09-30"
}
```

### Create New Patient
- **Endpoint:** `POST /api/v1/patients`
- **Description:** Registers a new patient in the system.
- **Response:** `201 Created` with a JSON object of the created patient.
   
Request Example:
```json
{
  "firstName": "Abbie",
  "lastName": "Erdman",
  "email": "abbie.erdman@gmail.com",
  "dateOfBirth": "1998-09-30",
  "address": "2625 Main Street, Miami, 33177"
}
```

Response Example:
```json
{
  "id": 6,
  "firstName": "Abbie",
  "lastName": "Erdman",
  "email": "abbie.erdman@gmail.com",
  "address": "2625 Main Street, Miami, 33177",
  "dateOfBirth": "1998-09-30"
}
```

### Update Patient Information
- **Endpoint:** `PUT /api/v1/patients/{id}`
- **Description:** Updates the information of an existing patient.
- **Response:** `200 OK` without a body.

Request Example:
```json
{
  "firstName": "Abbie",
  "lastName": "Erdman",
  "email": "abbie_erdman@gmail.com",
  "address": "3456 Fox Street, California, 32121",
  "dateOfBirth": "1980-09-30"
}
```

### Delete Patient
- **Endpoint:** `DELETE /api/v1/patients/{id}`
- **Description:** Deletes a patient record from the system.
- **Response:** `204 No Content` without a body.

---

## 📘 Documentation & Testing

### OpenAPI / Swagger
Once service is running, you can access the interactive API documentation:

http://localhost:9081/swagger-ui.html

![Alt text](../../__assets/images/patient-openapi-documentation.png?raw=true "OpenAPI Documentation Example")

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

ADD target/patient-service.jar patient-service.jar

EXPOSE 9081

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/patient-service.jar"]
```

Execute the command below to run the container individually (requires database setup):

```bash
docker run -e SPRING_PROFILES_ACTIVE=local -p 9081:9081 patient-service
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