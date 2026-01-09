![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Doctor Microservice

## 📋 Overview

The Doctor Service API is responsible for managing the lifecycle of doctor profiles within the system. It provides endpoints to create, retrieve, update, and delete medical staff records, including essential details such as specialties, departments, and contact information.

---

## 🏗️ Architecture

![Alt text](../../__assets/images/doctor-architecture.png?raw=true "Architecture Diagram")


---

## ️🗄️ Database

![Alt text](../../__assets/images/doctor-der.png?raw=true "Entity Relationship Diagram")

---

## 📚 API Endpoints

### List All Doctors
- **Endpoint:** `GET /api/v1/doctors`
- **Description:** Retrieves a list of all registered doctors.
- **Response:** `200 OK` with a JSON array of doctor objects.

Response Example:
```json
{
  "content": [
    {
      "id": 6,
      "firstName": "Zachary",
      "lastName": "Cronin",
      "title": "Dr.",
      "speciality": "Primary Care",
      "email": "zachary.cronin@medicalclinic.com",
      "phone": "898-538-0178",
      "department": "Primary Care"
    },
    {
      "id": 5,
      "firstName": "Suzanne",
      "lastName": "Brekke",
      "title": "Dr.",
      "speciality": "Primary Care",
      "email": "suzanne.brekke@medicalclinic.com",
      "phone": "562-352-3911",
      "department": "Primary Care"
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
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "first": true,
  "numberOfElements": 2,
  "empty": false
}
```

### Retrieve Doctor by ID
- **Endpoint:** `GET /api/v1/doctors/{id}`
- **Description:** Fetches details of a specific doctor by their ID.
- **Response:** `200 OK` with a JSON object of the doctor.

Response Example:
```json
{
  "id": 6,
  "firstName": "Zachary",
  "lastName": "Cronin",
  "title": "Dr.",
  "speciality": "Primary Care",
  "email": "zachary.cronin@medicalclinic.com",
  "phone": "898-538-0178",
  "department": "Primary Care"
}
```

### Create New Doctor
- **Endpoint:** `POST /api/v1/doctors`
- **Description:** Registers a new doctor in the system.
- **Response:** `201 Created` with a JSON object of the created doctor.
   
Request Example:
```json
{
  "firstName": "Zachary",
  "lastName": "Cronin",
  "title": "Dr.",
  "specialityId": 1,
  "email": "zachary.cronin@medicalclinic.com",
  "phone": "898-538-0178",
  "department": "Primary Care"
}
```

Response Example:
```json
{
  "id": 6,
  "firstName": "Zachary",
  "lastName": "Cronin",
  "title": "Dr.",
  "speciality": "Primary Care",
  "email": "zachary.cronin@medicalclinic.com",
  "phone": "898-538-0178",
  "department": "Primary Care"
}
```

### Update Doctor Information
- **Endpoint:** `PUT /api/v1/doctors/{id}`
- **Description:** Updates the information of an existing doctor.
- **Response:** `200 OK` without a body.

Request Example:
```json
{
  "firstName": "Zachary",
  "lastName": "Cronin",
  "title": "Dr.",
  "speciality": 2,
  "email": "zachary_cronin@medicalclinic.com",
  "phone": "654-723-5432",
  "department": "Primary Care"
}
```

### Delete Doctor
- **Endpoint:** `DELETE /api/v1/doctors/{id}`
- **Description:** Deletes a doctor record from the system.
- **Response:** `204 No Content` without a body.

---

## 📘 Documentation & Testing

### OpenAPI / Swagger
Once service is running, you can access the interactive API documentation:

http://localhost:9082/swagger-ui.html

![Alt text](../../__assets/images/doctor-openapi-documentation.png?raw=true "OpenAPI Documentation Example")

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

ADD target/doctor-service.jar doctor-service.jar

EXPOSE 9082

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/doctor-service.jar"]
```

Execute the command below to run the container individually (requires database setup):

```bash
docker run -e SPRING_PROFILES_ACTIVE=local -p 9082:9082 doctor-service
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