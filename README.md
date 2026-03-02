# 🏦 Bank Services – Microservices
![Docker](https://img.shields.io/badge/docker-ready-blue)

Bank system based on **microservices and event-driven architecture**.

---
## 🧱 Architecture

System is divided into several microservices, each handling specific funtions. Below is provided an overview of each microservice:


### 1. API Gateway 🌐

* Description: The single entry point for all client requests, routing them to the appropriate microservices.
* Technology: Spring Cloud Gateway and Redis
* Features: Security handling, routing and rate limiting.

### 2. Authentication service 🔐

* Description: Handles user authentication, token and identity management.
* Technology: Keycloak
* Features: User registration, login and authentication tokens



### 🔁 Event flow
![Event-Driven-Flow](diagrams/event-driven-flow-diagram.png)

---

## ⚙️ Configuration

### Enviroment variables

Sensitive configuration for spring booot were stored in a .env file, such as:

```properties
SPRING_DATASOURCE_URL: ${CLIENT_PERSON_URL}
SPRING_DATASOURCE_USERNAME: ${CLIENT_PERSON_USER}
SPRING_DATASOURCE_PASSWORD: ${CLIENT_PERSON_PASS}
```

---
## 📂 Project Structure

```text
bank-services/
│
├── common-lib/
│   ├── src/main/java/bank/common_lib
│   ├── enumeration
│   ├── event/dto
|
├── client-person/
│   ├── db/init
│   ├── src/main/java
│   ├── src/test/java
│   ├── Dockerfile
│   └── application.properties
│
├── account-movement/
│   ├── db/init
│   ├── src/main/java
│   ├── Dockerfile
│   └── application.properties
|
├── report-service/
│   ├── db/init
│   ├── src/main/java
│   ├── Dockerfile
│   └── application.properties
│
├── docker-compose.yml
└── README.md

```
---

## 🛠️ Stack

* Java 17+
* Spring Boot
* Spring Kafka
* Spring Cloud
* Apache Kafka (KRaft mode)
* Keycloak
* Redis
* PostgreSQL
* Docker & Docker Compose
* JUnit 5 / Mockito
* Testcontainers

---

## 🧪 Testing

Includes **unit and integration** for client-person service:

* Controllers
* Services

---

## 🌱 Future imrpovements

* Use **SPRING CLOUD GATEWAY** for load balancing.
* Implement GitHub Actions for CI/CD pipelines

---

## 👤 Author

**Oscar Vega**
Backend Developer – Spring Boot | Microservices 
