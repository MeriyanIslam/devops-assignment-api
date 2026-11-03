# Spring Boot DevOps Deployment with CI/CD

A hands-on DevOps project demonstrating the deployment and automation of a Spring Boot REST API integrated with PostgreSQL, Nginx, systemd, Tailscale, and GitHub Actions.

The project focuses on building a reliable deployment workflow with automated builds, service restarts, health checks, and REST API testing.

## Project Overview

This project deploys a Java Spring Boot REST API with PostgreSQL as its database. Nginx acts as a reverse proxy, while systemd manages the application as a Linux service.

A GitHub Actions self-hosted runner automates the CI/CD pipeline, allowing every push to the `main` branch to trigger the build, verification, deployment, and health-check process.

## Architecture

```text
                 Developer
                     |
                     | git push
                     v
              GitHub Repository
                     |
                     v
             GitHub Actions
                     |
                     v
          Self-Hosted Runner
                     |
              Maven Build
                     |
              Build & Verify
                     |
                Deploy JAR
                     |
                     v
              systemd Service
                     |
                     v
              Spring Boot API
                Port: 8080
                     |
                     v
                PostgreSQL
                Port: 5432

Client / Postman
       |
       | HTTP :80
       v
     Nginx
       |
       | Reverse Proxy
       v
Spring Boot API :8080

Remote PostgreSQL Access:
Tailscale Secure Network
```

## Technologies Used

| Category | Technologies |
|---|---|
| Backend | Java 17, Spring Boot |
| Build Tool | Maven |
| Database | PostgreSQL |
| Reverse Proxy | Nginx |
| Service Management | systemd |
| Secure Networking | Tailscale |
| CI/CD | GitHub Actions |
| Runner | GitHub Self-Hosted Runner |
| API Testing | Postman |
| Operating System | Manjaro Linux |

## Key Features

- RESTful API development using Spring Boot.
- PostgreSQL database integration.
- Secure database access through Tailscale.
- Nginx reverse proxy configuration.
- Application lifecycle management using systemd.
- Automated Maven build and verification.
- GitHub Actions self-hosted CI/CD pipeline.
- Automated JAR deployment and service restart.
- Application health monitoring through Spring Boot Actuator.
- API testing using Postman.

## REST API Endpoints

Base URL:

```text
http://localhost
```

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/tasks` | Retrieve all tasks |
| GET | `/api/tasks/{id}` | Retrieve a task by ID |
| POST | `/api/tasks` | Create a new task |
| PUT | `/api/tasks/{id}` | Update an existing task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| GET | `/actuator/health` | Application health check |

### Example POST Request

**Endpoint:**

```http
POST /api/tasks
```

**Request Body:**

```json
{
  "title": "Learn DevOps",
  "description": "Spring Boot deployment with CI/CD"
}
```

## CI/CD Pipeline

The project uses GitHub Actions with a self-hosted runner installed on the deployment machine.

### Pipeline Workflow

1. Developer pushes code to the `main` branch.
2. GitHub Actions triggers the workflow.
3. Self-hosted runner receives the job.
4. Source code is checked out.
5. Java 17 is configured.
6. Maven builds and verifies the application.
7. The generated JAR is deployed to `/opt/devops-assignment-api/`.
8. systemd restarts the Spring Boot service.
9. Nginx health endpoint is checked.
10. Deployment is marked successful if the health check passes.

### Workflow File

```text
.github/workflows/deploy.yml
```

### Deployment Trigger

```yaml
on:
  push:
    branches:
      - main
  workflow_dispatch:
```

## Deployment Configuration

### Application Service

The Spring Boot application runs as a systemd service:

```text
devops-assignment-api.service
```

Useful commands:

```bash
sudo systemctl status devops-assignment-api
sudo systemctl restart devops-assignment-api
sudo systemctl enable devops-assignment-api
```

### Nginx Reverse Proxy

Nginx listens on port `80` and forwards incoming requests to the Spring Boot application on port `8080`.

### PostgreSQL

The application connects to PostgreSQL using environment-based credentials.

Database configuration is managed through:

```text
/etc/devops-assignment-api.env
```

Sensitive credentials are kept outside the Git repository.

### Tailscale

Tailscale provides secure private-network connectivity for PostgreSQL access without exposing the database directly to the public internet.

## Local Development

### Prerequisites

- Java 17
- Maven
- PostgreSQL
- Git

### Clone Repository

```bash
git clone https://github.com/MeriyanIslam/devops-assignment-api.git

cd devops-assignment-api
```

### Configure Database

Create the PostgreSQL database and user, then configure the database connection in:

```text
src/main/resources/application.properties
```

Set the database password through the `DB_PASSWORD` environment variable.

### Build Application

```bash
./mvnw clean verify
```

### Run Application

```bash
export DB_PASSWORD='your_database_password'

./mvnw spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

## Health Check

Check application health through Nginx:

```bash
curl http://localhost/actuator/health
```

Expected response:

```json
{
  "status": "UP"
}
```

## Project Structure

```text
devops-assignment-api/
├── .github/
│   └── workflows/
│       └── deploy.yml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/meriyan/devops/assignment/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## What I Learned

Through this project, I gained practical experience with:

- Linux server administration and service management.
- Deploying Java applications in a Linux environment.
- Configuring PostgreSQL and database connectivity.
- Implementing Nginx reverse proxy.
- Managing application services using systemd.
- Setting up secure private networking with Tailscale.
- Building automated CI/CD pipelines with GitHub Actions.
- Configuring and managing self-hosted runners.
- Performing REST API testing and deployment verification.

## Author

**S. M. Meriyan Islam**

- GitHub: [@MeriyanIslam](https://github.com/MeriyanIslam)
- LinkedIn: [S. M. Meriyan Islam](https://www.linkedin.com/in/s-m-meriyan-islam-543085329/)

---

⭐ If you find this project useful, feel free to explore the repository and give it a star.

**Built as a hands-on DevOps deployment and automation project.**
