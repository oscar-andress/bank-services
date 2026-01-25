# 🏦 Bank Services – Microservices
![Docker](https://img.shields.io/badge/docker-ready-blue)

Bank system based on **microservices and event-driven architecture**.

---
## 🧱 Architecture

System have three main microservices which communicates asynchronically using **Kafka**

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
* Apache Kafka (KRaft mode)
* PostgreSQL
* Docker & Docker Compose
* JUnit 5 / Mockito
* Testcontainers (tests de integración)

---

## 🧪 Testing

Includes **unit and integration** for client-person service:

* Controllers
* Services

---

## 🌱 Future imrpovements

* Implement **SPRING SECURITY** for token authentication.
* Implement **SPRING CLOUD GATEWAY** for token validation and load balancers.
* Implement GitHub Actions for CI/CD pipelines

---

## 👤 Author

**Oscar Vega**
Backend Developer – Spring Boot | Microservices 
