# Drone Delivery API

## Overview

Drone Delivery API is a Spring Boot REST application that manages drones and medication deliveries.

The application provides REST APIs to:

- Register a drone
- Load a drone with medication
- Check loaded medications for a specific drone
- Check available drones for loading
- Check a drone's battery level

The application also includes automatic drone state transitions using Spring Scheduler.

---

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- H2 In-Memory Database
- Maven

---

## Prerequisites

Before running the project, make sure you have:

- Java 17 or later
- Git

> Maven installation is **not required** because this project uses the Maven Wrapper (`mvnw`).

---

## Clone the Repository

```bash
git clone https://github.com/<your-github-username>/drone-delivery.git
```

Replace `<your-github-username>` with your GitHub username.

---

## Build the Project

### Windows

```powershell
.\mvnw.cmd clean package
```

### Linux / macOS

```bash
./mvnw clean package
```

---

## Run the Application

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

---

## Run Unit Tests

### Windows

```powershell
.\mvnw.cmd test
```

### Linux / macOS

```bash
./mvnw test
```

---

## Database

This project uses an **H2 In-Memory Database**.

Sample data is automatically preloaded when the application starts.

Since the database is in-memory, all data is removed whenever the application stops.

### Accessing the H2 Console

Open your browser and navigate to:

```
http://localhost:8080/h2-console
```

Use the following connection settings:

**JDBC URL**

```
jdbc:h2:mem:testdb
```

**Username**

```
sa
```

**Password**

Leave blank.

Click **Connect** to view the database.

---

## Preloaded Sample Data

When the application starts, one sample drone is automatically inserted.

### Drone

| Property | Value |
|----------|-------|
| Serial Number | DRONE001 |
| Model | HEAVYWEIGHT |
| Weight Limit | 1000 g |
| Battery Capacity | 100% |
| State | IDLE |

### Preloaded Medications

| Name | Weight | Code |
|------|-------:|------|
| PARACETAMOL | 200 g | MED_001 |
| VITAMIN_C | 100 g | MED_002 |

---

## Drone State Transition

The application uses **Spring Scheduler** to automatically transition drone states.

```
LOADED
   ↓
DELIVERING
   ↓
DELIVERED
   ↓
RETURNING
   ↓
IDLE
```

After each completed delivery, the drone battery is reduced by **10%**.

---

# REST API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/drones` | Register a new drone |
| POST | `/api/drones/{id}/load` | Load medication onto a drone |
| GET | `/api/drones/{id}/medications` | Retrieve medications loaded on a drone |
| GET | `/api/drones/available` | Retrieve drones available for loading |
| GET | `/api/drones/{id}/battery` | Retrieve a drone's battery level |

---

# Testing the APIs (cURL)

## 1. Register a Drone

```bash
curl -X POST http://localhost:8080/api/drones \
-H "Content-Type: application/json" \
-d '{
  "serialNumber":"DRONE002",
  "model":"HEAVYWEIGHT",
  "batteryCapacity":100
}'
```

---

## 2. Load Medication

Replace **{id}** with the drone ID returned from the Register Drone API.

```bash
curl -X POST http://localhost:8080/api/drones/{id}/load \
-H "Content-Type: application/json" \
-d '{
  "name":"PARACETAMOL",
  "weight":200,
  "code":"MED_001",
  "image":"paracetamol.png"
}'
```

---

## 3. Check Loaded Medications

```bash
curl http://localhost:8080/api/drones/{id}/medications
```

---

## 4. Check Available Drones

```bash
curl http://localhost:8080/api/drones/available
```

---

## 5. Check Drone Battery Level

```bash
curl http://localhost:8080/api/drones/{id}/battery
```

---

## Project Structure

```
src
└── main
    └── java
        └── com.drone.delivery
            ├── controller
            ├── dto
            ├── entity
            ├── enums
            ├── repository
            ├── scheduler
            ├── service
            └── DroneDeliveryApplication.java
```

---

## Assumptions

- Drone weight limits are determined by the selected drone model:
  - LIGHTWEIGHT = 250 g
  - MIDDLEWEIGHT = 500 g
  - CRUISERWEIGHT = 750 g
  - HEAVYWEIGHT = 1000 g
- A drone cannot be loaded when its battery level is below 25%.
- Drone states transition automatically using Spring Scheduler.
- Drone battery capacity decreases by 10% after each completed delivery.
- API authentication is intentionally omitted as specified in the assessment.
- Drone communication with physical hardware is outside the scope of this project.

---

## Notes

- All request and response bodies use JSON.
- The application is buildable and runnable using the Maven Wrapper.
- The project uses an H2 in-memory database.
- Sample data is automatically preloaded during application startup.
- Unit tests are included for one method from the service layer.