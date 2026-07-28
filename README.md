# 🩸 BloodBridge

> A secure and scalable Blood Donation Management System built with **Spring Boot** that connects blood donors with patients during emergencies.

BloodBridge provides a secure RESTful API with **JWT Authentication**, **Role-Based Authorization**, **Donor Management**, **Blood Request Management**, **Notification System**, **Dynamic Search**, and **Global Exception Handling**.

---

## 📌 Overview

BloodBridge is designed to simplify the blood donation process by allowing users to:

- Register and authenticate securely
- Create and manage donor profiles
- Request blood during emergencies
- Match available donors by blood group and location
- Receive notifications for blood requests and responses
- Manage donation responses securely

---

# 🚀 Features

## 🔐 Authentication & Security

- JWT Authentication
- BCrypt Password Encryption
- Spring Security
- Stateless Authentication
- Secure REST APIs

---

## 👥 User Management

- User Registration
- User Login
- View Authenticated Profile
- Update Profile

---

## 🩸 Donor Management

- Create Donor Profile
- View Donor Profile
- Update Donor Information
- Delete Donor Profile
- Update Availability Status

---

## 🏥 Blood Request Management

- Create Blood Request
- View Blood Requests
- Update Request Status
- Delete Blood Request
- Automatically Match Eligible Donors

---

## 🤝 Donation Response

- Accept Blood Request
- Reject Blood Request
- Prevent Duplicate Responses
- Prevent Multiple Acceptances
- Automatically Update Request Status

---

## 🔔 Notification System

- Notify Matching Donors
- Notify Requester When Request Is Accepted
- View Notifications
- Read / Unread Notification Support

---

## 🔎 Search & Filtering

Dynamic donor search using **Spring Data JPA Specification**

Search by:

- Blood Group
- District
- City
- Availability

Supports:

- Dynamic Filtering
- Pagination
- Sorting (Ascending & Descending)

---

## 🛡 Role-Based Authorization

### 👤 USER

- Create Blood Request
- Manage Own Profile
- Become a Donor

### 🩸 DONOR

- View Donor Profile
- Accept Blood Requests
- Reject Blood Requests
- Manage Availability

### 👮 ADMIN

- Manage Users
- Manage Donors
- Manage Blood Requests

---

## ⚠️ Exception Handling

Centralized exception handling using `@RestControllerAdvice`

Custom Exceptions:

- ResourceNotFoundException
- BadRequestException
- ConflictException
- ForbiddenException

---

## ✅ Request Validation

Bean Validation is implemented using:

- `@NotBlank`
- `@NotNull`
- `@Email`
- `@Pattern`
- `@Size`
- `@Valid`

---

## 📄 API Documentation

Interactive API documentation using **Swagger OpenAPI**.

---

# 🏗 Project Architecture

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
PostgreSQL
```

---

# 🛠 Tech Stack

| Technology | Description |
|------------|-------------|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | ORM |
| Hibernate | ORM Framework |
| PostgreSQL | Database |
| JWT | Authentication |
| Maven | Build Tool |
| Lombok | Boilerplate Reduction |
| Swagger OpenAPI | API Documentation |

---

# 📂 Project Structure

```
src
└── main
    ├── java
    │   └── com.robindas.bloodbridge
    │       ├── config
    │       ├── controller
    │       ├── dto
    │       ├── exception
    │       ├── filter
    │       ├── model
    │       ├── repository
    │       ├── service
    │       ├── specification
    │       ├── util
    │       └── BloodBridgeApplication
    │
    └── resources
        ├── application.properties
        └── static
```

---

# 🔐 Authentication Flow

```
Client
   │
   ▼
Login API
   │
   ▼
AuthenticationManager
   │
   ▼
UserDetailsService
   │
   ▼
Generate JWT Token
   │
   ▼
Bearer Token
   │
   ▼
JWT Filter
   │
   ▼
Protected APIs
```

---

# 🩸 Blood Request Workflow

```
User Creates Blood Request
            │
            ▼
Blood Request Saved
            │
            ▼
Find Matching Donors
            │
            ▼
Create Notifications
            │
            ▼
Donor Accepts / Rejects
            │
            ▼
Request Status Updated
            │
            ▼
Requester Gets Notification
```

---

# 📌 REST API

## Authentication

| Method | Endpoint |
|--------|----------|
| POST | `/api/v1/auth/register` |
| POST | `/api/v1/auth/login` |

---

## Users

| Method | Endpoint |
|--------|----------|
| GET | `/api/v1/users/me` |
| PUT | `/api/v1/users/me` |

---

## Donors

| Method | Endpoint |
|--------|----------|
| POST | `/api/v1/donors/me` |
| GET | `/api/v1/donors/me` |
| PUT | `/api/v1/donors/me` |
| DELETE | `/api/v1/donors/delete` |
| GET | `/api/v1/donor/search` |

---

## Blood Requests

| Method | Endpoint |
|--------|----------|
| POST | `/api/v1/blood-request/create` |
| GET | `/api/v1/blood-request/{id}` |
| GET | `/api/v1/blood-request/all` |
| PUT | `/api/v1/blood-request/{id}/status` |
| DELETE | `/api/v1/blood-request/{id}` |

---

## Donation Response

| Method | Endpoint |
|--------|----------|
| POST | `/api/v1/response/{requestId}/accept` |
| POST | `/api/v1/response/{requestId}/reject` |

---

## Notifications

| Method | Endpoint |
|--------|----------|
| GET | `/api/v1/notification/all` |
| PUT | `/api/v1/notification/{id}/read` |

---

## Admin

| Method | Endpoint |
|--------|----------|
| GET | `/api/v1/admin/users` |
| GET | `/api/v1/admin/donors` |
| GET | `/api/v1/admin/blood-request` |

---

# ⚙️ Getting Started

## Clone Repository

```bash
git clone https://github.com/das-robi/BloodBridge.git
```

## Navigate

```bash
cd BloodBridge
```

## Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/project2
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

## Run

```bash
mvn spring-boot:run
```

---

# 📖 Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

# 📈 Current Progress

- ✅ JWT Authentication
- ✅ Spring Security
- ✅ Role-Based Authorization
- ✅ Donor Management
- ✅ Blood Request Management
- ✅ Donation Response
- ✅ Notification System
- ✅ Global Exception Handling
- ✅ Validation
- ✅ Pagination
- ✅ Sorting
- ✅ Dynamic Filtering (JPA Specification)
- ✅ Swagger Documentation

---

# 🚧 Planned Improvements

- Docker Support
- Unit Testing (JUnit & Mockito)
- Logging (SLF4J)
- Email Notifications
- SMS Notifications
- Real-Time Notifications
- Donation History
- Dashboard & Analytics
- CI/CD Pipeline
- Cloud Deployment

---

## ⭐ Support

If you found this project helpful, consider giving it a **⭐ Star** on GitHub.
