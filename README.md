# Patient System Management

A **Patient System Management** platform built with **Java 21**, **Spring Boot 3.5.9**, and **MySQL 8**, using a **microservices architecture**.

The system consists of three independent microservices:
- **Patient Service**
- **Doctor Service**
- **Appointment Service**

Microservices communicate via **REST** using **Spring Cloud OpenFeign**, and all APIs are documented using **OpenAPI (Swagger)**.

---

## 🧱 Architecture Overview

- **Appointment Service** integrates with:
    - **Patient Service** to retrieve patient information
    - **Doctor Service** to retrieve doctor information
- Each service has:
    - Its own database schema
    - Its own Dockerfile
- A root-level `docker-compose.yml` orchestrates all services.

---

## 🗺️ Architecture Diagram (PlantUML)

```plantuml
@startuml
!theme plain

package "Patient System Management" {

  [Patient Service
Port: 9081] --> (MySQL)
  [Doctor Service
Port: 9082] --> (MySQL)
  [Appointment Service
Port: 9083] --> (MySQL)

  [Appointment Service
Port: 9083] --> [Patient Service
Port: 9081] : OpenFeign REST
  [Appointment Service
Port: 9083] --> [Doctor Service
Port: 9082] : OpenFeign REST
}

@enduml
```

---

## 🔧 Technology Stack

- Java 21
- Spring Boot 3.5.9
- Spring Data JPA
- Spring Cloud OpenFeign
- MySQL 8
- OpenAPI / Swagger
- Docker & Docker Compose

---

## 🚀 Microservices

| Service | Port | Description |
|-------|------|------------|
| Patient Service | 9081 | Manages patient information |
| Doctor Service | 9082 | Manages doctor information |
| Appointment Service | 9083 | Manages appointments and integrates with patient & doctor services |

---

## 📘 API Documentation (Swagger)

Once services are running, access OpenAPI documentation:

- **Patient Service**  
  http://localhost:9081/swagger-ui.html

- **Doctor Service**  
  http://localhost:9082/swagger-ui.html

- **Appointment Service**  
  http://localhost:9083/swagger-ui.html

---

## 📌 Endpoints

### 🩺 Doctor Service (`/v1/doctors`)

| Method | Endpoint | Description |
|------|---------|------------|
| POST | `/v1/doctors` | Create a doctor |
| GET | `/v1/doctors` | List doctors (paged) |
| GET | `/v1/doctors/{id}` | Get doctor by ID |
| PUT | `/v1/doctors/{id}` | Update doctor |
| DELETE | `/v1/doctors/{id}` | Delete doctor |

#### Create Doctor
**POST /v1/doctors**
```json
{
  "firstName": "Hailie",
  "lastName": "Goodwin",
  "title": "Dr.",
  "specialityId": 1,
  "email": "hailie.goodwin@medicalclinic.com",
  "phone": "715-386-4575",
  "department": "Primary Care"
}
```

**Response – 201**
```json
{
  "id": 1,
  "firstName": "Hailie",
  "lastName": "Goodwin",
  "title": "Dr.",
  "speciality": "Primary Care",
  "email": "hailie.goodwin@medicalclinic.com",
  "phone": "581-984-9786",
  "department": "Primary Care"
}
```

---

### 🧑 Patient Service (`/v1/patients`)

| Method | Endpoint | Description |
|------|---------|------------|
| POST | `/v1/patients` | Create patient |
| GET | `/v1/patients` | List patients |
| GET | `/v1/patients/{id}` | Get patient |
| PUT | `/v1/patients/{id}` | Update patient |
| DELETE | `/v1/patients/{id}` | Delete patient |

#### Create Patient
```json
{
  "firstName": "Joanny",
  "lastName": "Auer",
  "email": "joanny.auer@gmail.com",
  "address": "7092 Pine Street, Houston, 77070",
  "dateOfBirth": "1996-04-23"
}
```

---

### 📅 Appointment Service (`/v1/appointments`)

| Method | Endpoint | Description |
|------|---------|------------|
| POST | `/v1/appointments` | Create appointment |
| GET | `/v1/appointments/{id}` | Get appointment |

#### Create Appointment
```json
{
  "title": "Knee Pain Consultation",
  "type": "CONSULTATION",
  "startTime": "2025-10-20T14:30:00",
  "patientId": 4,
  "doctorId": 1,
  "description": "Patient is experiencing persistent knee pain after running."
}
```

**Response – 201**
```json
{
  "id": 2,
  "patient": {
    "id": 4,
    "firstName": "Joanny",
    "lastName": "Auer",
    "email": "joanny.auer@gmail.com"
  },
  "doctor": {
    "id": 1,
    "firstName": "Hailie",
    "lastName": "Goodwin",
    "title": "Dr.",
    "speciality": "Primary Care"
  },
  "status": "SCHEDULED"
}
```

---

## ▶️ Running Locally (Spring Boot)

From each microservice root directory:

```bash
./mvnw spring-boot:run
```

Or:

```bash
mvn clean package
java -jar target/*.jar
```

---

## 🐳 Running with Docker

Each microservice contains its own `Dockerfile`.

### Build Image
```bash
docker build -t patient-service .
docker build -t doctor-service .
docker build -t appointment-service .
```

### Run Container
```bash
docker run -p 9081:9081 patient-service
docker run -p 9082:9082 doctor-service
docker run -p 9083:9083 appointment-service
```

---

## 🐳 Docker Compose

### Start All Services
```bash
docker compose up -d
```

### Stop Containers
```bash
docker compose stop
```

### Stop & Remove Containers
```bash
docker compose down --rmi all
```

---

## ✅ Notes

- Appointment Service uses **OpenFeign** to call:
    - Patient Service
    - Doctor Service
- API contracts are fully documented using **OpenAPI**
- Designed for scalability and independent deployment

---

📌 **Author:** Patient System Management Project
