# Midas Core – Software Engineering Job Simulation (JPMorgan Chase & Co.)

This repository contains my complete implementation of the **JPMorgan Chase & Co. Software Engineering Job Simulation** hosted on **Forage**. The project simulates a real-world, event-driven financial transaction system built using **Spring Boot**, **Apache Kafka**, **H2 Database**, and **REST APIs**.

---

## 📌 Project Overview

**Midas Core** is a backend service responsible for:

* Consuming financial transactions from Kafka
* Validating and processing transactions
* Persisting users and transactions in a relational database
* Integrating with an external Incentive REST API
* Exposing REST endpoints to query user balances

The project is implemented incrementally across multiple tasks, closely mirroring enterprise backend development workflows.

---

## 🏗️ System Architecture

```
┌──────────────┐        Kafka        ┌────────────────┐
│ Transaction  │  ───────────────▶  │  Midas Core    │
│  Producer    │                   │ (Spring Boot)  │
└──────────────┘                   └───────┬────────┘
                                            │
                         REST (POST)        │
                    ┌───────────────────┐  │
                    │ Incentive API     │◀─┘
                    │ (External Service)│
                    └───────────────────┘
                                            │
                                JPA / H2 DB │
                                            ▼
                                   ┌────────────────┐
                                   │  H2 Database   │
                                   └────────────────┘
```

---

## 🚀 Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot
* **Messaging:** Apache Kafka (Embedded Kafka for testing)
* **Database:** H2 (In-memory)
* **ORM:** Spring Data JPA
* **REST Client:** RestTemplate
* **Build Tool:** Maven
* **Testing:** JUnit, Spring Boot Test

---

## 🧩 Implemented Features (Task-wise)

### ✅ Task 1 & 2 – Kafka Integration

* Configured Kafka producer and consumer
* Consumed transaction events from Kafka topics
* Deserialized transactions using Spring Kafka

---

### ✅ Task 3 – H2 Database Integration

* Integrated H2 in-memory database
* Created `UserRecord` JPA entity
* Validated transactions based on:

  * Sender existence
  * Recipient existence
  * Sender balance ≥ transaction amount
* Updated sender & recipient balances atomically

---

### ✅ Task 4 – Incentive REST API Integration

* Integrated external Incentive API using `RestTemplate`
* Posted validated transactions to `/incentive` endpoint
* Received incentive amount and:

  * Added incentive to recipient balance
  * Did NOT deduct incentive from sender

---

### ✅ Task 5 – REST API Controller

* Exposed REST endpoint to query user balances

#### Endpoint Details:

```
GET /balance?userId={id}
```

* Returns JSON serialized `Balance` object
* Returns balance `0` if user does not exist
* Runs alongside Kafka consumers

---

## 🔍 Sample API Response

```json
{
  "amount": 1326.98
}
```

---

## 🧪 Testing

Each task includes dedicated test classes:

* `TaskThreeTests`
* `TaskFourTests`
* `TaskFiveTests`

To run a specific task test:

```bash
mvn -Dtest=TaskFiveTests test
```

⚠️ **Note:** Ensure the Incentive API JAR is running before executing Task 4 or Task 5 tests.

---

## ▶️ Running Incentive API

```bash
java -jar transaction-incentive-api.jar --server.port=8085
```

(Update Midas Core configuration accordingly if port differs.)

---

## 📂 Project Structure

```
src/main/java
├── clients          # REST client for Incentive API
├── component        # Database conduit
├── entity           # JPA entities
├── foundation       # Core domain models
├── kafkaListener    # Kafka consumers
├── controller       # REST API controllers
└── repository       # Spring Data JPA repositories
```

---

## 🧠 Key Learnings

* Event-driven architecture using Kafka
* Transactional data consistency with JPA
* REST API integration and contract-based design
* Clean separation of concerns in Spring Boot
* Real-world backend system design patterns

---

## 📜 Certificate

This project was completed as part of:

**JPMorgan Chase & Co. – Software Engineering Job Simulation**
Platform: Forage
Completion Date: January 2026

---

## 👤 Author

**Shrihari R. Kulkarni**
Computer Engineering Student
Backend | Java | Spring Boot | Kafka

---

## ⭐ Acknowledgements

* JPMorgan Chase & Co.
* Forage Platform

---

> This repository represents a hands-on simulation of enterprise-grade backend development practices used in financial systems.
