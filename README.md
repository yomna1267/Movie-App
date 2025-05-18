
# Movie-App

A full-stack movie management application with an Angular frontend and a Spring Boot backend.  
Admins can fetch movies from the OMDB API (`https://www.omdbapi.com`) and add them to the app’s database.  
Regular users can then browse these added movies and view their detailed information and rate them.

---

## 📑 Table of Contents

- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation and Running](#installation-and-running)

  - [Clone the repository](#1-clone-the-repository)
  - [Run with Docker Compose (Recommended)](#2-run-with-docker-compose-recommended)
  - [Backend Setup and Run](#3-backend-setup-and-run)
  - [Frontend Setup and Run](#4-frontend-setup-and-run)
- [Usage](#usage)
- [Notes](#notes)

---

## Project Structure

```plaintext
Movie-App/
│
├── backend/
│   └── demo/               # Spring Boot backend application
│       ├── src/
│       ├── pom.xml
│       └── ...
│
└── frontend/               # Angular frontend application
    ├── src/
    ├── angular.json
    ├── package.json
    └── ...
```

---

## Prerequisites

- Java JDK 11 or higher  
- Maven  
- Node.js (v14 or higher)  
- npm  
- Angular CLI (optional, for development)  
- PostgreSQL (running and configured)

---

## Installation and Running

### 1. Clone the repository

```bash
git clone https://github.com/yomna1267/Movie-App.git
cd Movie-App
```

---

### 2. Run with Docker Compose (Recommended)

If you have Docker and Docker Compose installed, you can start the full app with one command:

```bash

docker-compose up --build

```

This runs without the need for installation of the prerequisites locally, as all services are containerized with Docker.

Backend will be accessible at `http://localhost:8080`.

Frontend will be accessible at `http://localhost:4200`.

### 3. Backend Setup and Run

#### 3.1 Configure PostgreSQL

Make sure PostgreSQL is running and a database is created (e.g. `movie_app_db`).  
Update the file:  
`backend/demo/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/movie_app_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

#### 3.2 Run the Backend

```bash
cd backend/demo
```

- Run using Maven:

```bash
mvn spring-boot:run
```

- Or build and run the jar:

```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

The backend runs at `http://localhost:8080`.

---

### 4. Frontend Setup and Run

In a new terminal:

```bash
cd frontend
npm install
ng serve
```

The frontend runs at `http://localhost:4200`.

---

## Usage

The application has three default users:

| Username | Password   | Role  |
|----------|------------|-------|
| admin    | adminpass  | Admin |
| yomna    | yomna      | Admin |
| user     | userpass   | User  |

- Admins can log in to manage movies (add, delete) from OMDB `https://www.omdbapi.com`.  
- Regular users can browse movies and view details.

Open `http://localhost:4200` in your browser and log in with one of the above accounts.

---

## Notes

- Run backend before frontend for API connectivity.  
- Ensure PostgreSQL database is up and credentials are correct.  
- Configure OMDB API key if needed.

---
