
# Patient Management System (PMS)

A Patient System Management platform built with Java 21, Spring Boot 3.5.9, and MariaDB, designed using a microservices architecture.

## 📋 Overview

The primary goal of this project is to demonstrate a complete microservices ecosystem, showcasing the infrastructure and code required to manage patient data, doctor information, and appointments. Additionally, it serves as a practical demonstration of deploying these microservices using three different strategies: [Docker Compose](https://docs.docker.com/compose/), [Docker Swarm](https://docs.docker.com/engine/swarm/), and [Kubernetes (K8s)](https://kubernetes.io/docs/).

The system consists of three independent microservices:

- **[ms-doctor](microservices/ms-doctor)**: Handles the creation, retrieval, and management of doctor information.
- **[ms-patient](microservices/ms-patient)**: Manages patient data, including personal details and medical history records.
- **[ms-appointment](microservices/ms-appointment)**: Orchestrates the scheduling of appointments, integrating with the Doctor and Patient services to validate and retrieve relevant information.

Key Technical Features:

- **Database**: Each microservice uses its own dedicated [MariaDB](https://mariadb.com/docs/general-resources/database-theory/introduction-to-relational-databases) schema. Table creation and initial data seeding are handled via specific scripts located within each service's resources.
- **Communication**: Synchronous communication between services is handled via REST using [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign).
- **Observability**: The project integrates ELK Stack ([Elasticsearch](https://www.elastic.co/docs/solutions/search), [Logstash](https://www.elastic.co/docs/reference/logstash/getting-started-with-logstash), [Kibana](https://www.elastic.co/guide/en/kibana/8.19/index.html)) for centralized logging and [Grafana](https://grafana.com/docs/grafana/latest/) & [Prometheus](https://prometheus.io/) for monitoring and metrics visualization.

---

## 🏗️ Architecture

This section presents the **high-level architecture** of the system.

![Alt text](__assets/pms-architecture.png?raw=true "Patient Management System Architecture")

---

## 🛠️ Technology Stack

- Language: Java 21
- Framework: Spring Boot 3.5.9
- Data: Spring Data JPA, MariaDB
- Cloud: Spring Cloud OpenFeign
- API Documentation: OpenAPI (Swagger)
- Containerization: Docker, Docker Compose, Docker Swarm
- Orchestration: Kubernetes (K8s), Helm
- Logging: ELK Stack (Elasticsearch, Logstash, Kibana)
- Monitoring: Prometheus, Grafana
- Performance Testing: Gatling

---

## 🗄️ Database

Each microservice persists data in its own isolated MariaDB database schema. This ensures data encapsulation and loose coupling between services.

![Alt text](__assets/pms-der.png?raw=true "Patient Management DER")

---

## 📚 API List

### 🩺 Doctor Service (/v1/doctors)

| Method | Endpoint           | Description          |
|--------|--------------------|----------------------|
| POST   | /v1/doctors        | Create a doctor      |
| GET    | /v1/doctors        | List doctors (paged) |
| GET    | /v1/doctors/{id}   | Get doctor by ID     |
| PUT    | /v1/doctors/{id}   | Update doctor        |
| DELETE | /v1/doctors/{id}   | Delete doctor        |

### 🧑 Patient Service (/v1/patients)

| Method | Endpoint            | Description           |
|--------|---------------------|-----------------------|
| POST   | /v1/patients        | Create a patient      |
| GET    | /v1/patients        | List patients (paged) |
| GET    | /v1/patients/{id}   | Get patient by ID     |
| PUT    | /v1/patients/{id}   | Update patient        |
| DELETE | /v1/patients/{id}   | Delete patient        |

### 📅 Appointment Service (/v1/appointments)

| Method | Endpoint              | Description            |
|--------|-----------------------|------------------------|
| POST   | /v1/appointments      | Create an appointment  |
| GET    | /v1/appointments/{id} | Get appointment by ID  |

---

## 📘 Documentation & Testing

### OpenAPI / Swagger
Once services are running, you can access the interactive API documentation:

- Patient Service: http://localhost:9081/swagger-ui.html
- Doctor Service: http://localhost:9082/swagger-ui.html
- Appointment Service: http://localhost:9083/swagger-ui.html

![Alt text](__assets/openapi-documentation.png?raw=true "OpenAPI Documentation Example")

### Postman Collection

A **Postman collection** is provided to test all APIs.
- Location: `/documentation/postman/PMS.postman_collection.json`

---

## 🔍 Centralized Logging

To effectively monitor and troubleshoot the distributed system, logs from all microservices are centralized using the ELK Stack.

### Logstash

**Logstash** acts as the data processing pipeline. It ingests logs from the microservices and transforms them before sending them to Elasticsearch.

Each microservice is configured with `logback-spring.xml` to send logs to Logstash when running in Docker:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    <include resource="org/springframework/boot/logging/logback/console-appender.xml"/>

    <property name="LOGSTASH_HOST" value="${LOGSTASH_HOST:-localhost}"/>
    <property name="LOGSTASH_PORT" value="${LOGSTASH_PORT:-5000}"/>

    <appender name="LOGSTASH"
              class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>${LOGSTASH_HOST}:${LOGSTASH_PORT}</destination>

        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp/>
                <logLevel/>
                <loggerName/>
                <message/>
                <mdc/>
                <arguments/>
                <stackTrace/>
            </providers>
        </encoder>
    </appender>

    <springProfile name="!docker">
        <root level="INFO">
            <appender-ref ref="CONSOLE"/>
        </root>
    </springProfile>

    <springProfile name="docker">
        <root level="INFO">
            <appender-ref ref="CONSOLE"/>
            <appender-ref ref="LOGSTASH"/>
        </root>
    </springProfile>

</configuration>
```

### Elasticsearch

**Elasticsearch** is a distributed, RESTful search and analytics engine. In this project, it stores and indexes the logs received from Logstash, allowing for fast and scalable searching.

### Kibana

**Kibana** is the visualization layer of the ELK stack. It connects to Elasticsearch to provide a user interface for searching, viewing, and interacting with the log data.

1. Access Kibana at http://localhost:5601.
2. Navigate to Stack Management > Index Patterns.
3. Create an index pattern matching pms-logs-* (as defined in the Logstash output).
4. Navigate to Discover. You should see JSON logs from the microservices arrive via the Logstash pipeline configured in the section.

![Alt text](__assets/kibana-discover-logs.png?raw=true "Kibana Log Discover Example")


---

## 📊 Monitoring

Monitoring ensures the health and performance of the microservices.

### Prometheus

**Prometheus** is an open-source systems monitoring and alerting toolkit. It scrapes and stores metrics as time series data.

Each microservice exposes metrics using the following configuration:

```yaml
management:
  endpoint:
    metrics:
      access: read_only
    prometheus:
      access: read_only
  endpoints:
    web:
      exposure:
        include: metrics,info,health,prometheus
  metrics:
    distribution:
      percentiles-histogram.http.server.requests: false
      percentiles.http.server.requests: 0.5, 0.9, 0.95, 0.99, 0.999
      sla.http.server.requests: 500ms, 2000ms

```
Access Prometheus at http://localhost:9090

![Alt text](__assets/prometheus-query.png?raw=true "Prometheus Query Example")

---

### Grafana

**Grafana** is an open-source analytics and interactive visualization platform. It connects to Prometheus to create rich dashboards for monitoring system metrics.

- Access: http://localhost:3000
- Username: `admin`
- Password: `admin`

![Alt text](__assets/grafana-dashboard.png?raw=true "Grafana Dashboard Example")

---

## ⚡ Performance Tests

### Gatling

**Gatling** was used to load test the microservices. It is a powerful tool for measuring system performance, scalability, and resilience under load.

#### Running Tests
The following Maven command triggers a load simulation that targets all three microservices:

```bash
mvn gatling:test  
          -Dgatling.simulationClass=com.pms.performance.simulation.PmsLoadSimulation  
          -DpatientUrl=http://localhost:9081  
          -DdoctorUrl=http://localhost:9082  
          -DappointmentUrl=http://localhost:9083  
          -Dusers=5  
          -DdurationMinutes=30
```

**Parameters:**
- `simulationClass`: Gatling simulation class
- `patientUrl`: Patient service URL
- `doctorUrl`: Doctor service URL
- `appointmentUrl`: Appointment service URL
- `users`: Number of virtual users
- `durationMinutes`: Test duration

![Alt text](__assets/gatling-results.png?raw=true "Gatling Test Results Example")

---

## 🚀 Deployment

This project demonstrates three distinct strategies for deploying the microservices.

### Docker Compose

**Docker Compose** is a tool for defining and running multi-container Docker applications. It is ideal for local development and simple deployments.

- Location: `/deployment/docker-compose/infrastructure`
- Includes configuration for:
  - Database
  - Logstash
  - Prometheus
  - Grafana

![Alt text](__assets/docker-compose-folder-structure.png?raw=true "Docker Compose Folder Structure")

Commands:

```bash
# Start all services
docker compose -f docker-compose.yml up -d

# Stop and remove containers
docker compose -f docker-compose.yml down
```

---

### Docker Swarm

**Docker Swarm** is a clustering and scheduling tool for Docker containers. It allows you to manage a cluster of Docker engines as a single virtual system. This setup is typically run on a cluster of virtual machines or EC2 instances.


#### Cluster Setup
On every node in the cluster, run the installation script found at /deployment/docker-swarm/scripts/install-docker.sh:

```bash
#!/bin/bash
echo "Starting Docker installation..."
sudo yum update -y
sudo yum install -y docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
sudo yum install -y git wget
echo "Docker version:"
docker --version
echo "Docker status:"
sudo systemctl status docker --no-pager
echo ""
echo "Installation complete! Please log out and log back in."
echo "Then test with: docker ps"
```
Run the script:

```bash
chmod +x install-docker.sh
./install-docker.sh
```

#### Build & Push Images

Microservices should have their images pushed to a Docker registry (e.g., Docker Hub).

```bash
# Build image
docker build -t joeltadeu/ms-patient:latest .

# Push image
docker push joeltadeu/ms-patient:latest
```

#### Swarm Deployment

##### Initialize Cluster:
```bash
docker swarm init
```

##### Add managers/workers using generated tokens.
```bash
# Add manager
docker swarm join-token manager

# Add worker
docker swarm join-token worker
```

##### Deploy Stack

```bash
docker stack deploy -c docker-stack.yml pms
```

##### Remove stack:

```bash
docker stack rm pms
```

---

### Kubernetes (K8s)

**Kubernetes** is an open-source system for automating deployment, scaling, and management of containerized applications. This deployment utilizes **Helm** for infrastructure components and **kubectl** for microservices.

#### Deployment Structure

Infrastructure (Helm): Located in `/deployment/k8s/infrastructure`.

![Alt text](__assets/kubernetes-folder-structure.png?raw=true "Kubernetes Folder Structure")

#### Deployment Commands

```bash
# 1. Create Namespace
kubectl apply -f deployment/k8s/namespace.yaml

# 2. Configure Helm Repositories (Add and Update)
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update

# 3. Apply Secrets and ConfigMaps
kubectl apply -f deployment/k8s/secrets/pms-mariadb-secret.yaml
kubectl apply -f deployment/k8s/infrastructure/logstash-configmap.yaml

# 4. Deploy Infrastructure using Helm
helm install elasticsearch deployment/k8s/infrastructure/elasticsearch-values.yaml
helm install prometheus deployment/k8s/infrastructure/prometheus-values.yaml
helm install grafana deployment/k8s/infrastructure/grafana-values.yaml
helm install logstash deployment/k8s/infrastructure/logstash-values.yaml
helm install mariadb deployment/k8s/infrastructure/mariadb-values.yaml

# 5. Deploy Microservices
kubectl apply -f deployment/k8s/microservices/

# 6. Deploy Ingress
kubectl apply -f deployment/k8s/ingress.yaml
```

---

## 🔨 Build Project & Running Locally

To build the entire project using Maven:

```bash
mvn clean install
```

### Running Locally
To run a microservice locally, navigate to its directory and use:

```bashbash
cd ms-doctor
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Running as a Container

Each microservice includes a **Dockerfile** to build a lightweight, secure container image using [Distroless](https://github.com/GoogleContainerTools/distroless).

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
