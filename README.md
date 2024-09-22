when you need help you can reach me from linkedn.
## Here it's Turkish Documentation
([https://github.com/alicancagdas/ispark/tree/presentation])
# ISPARK Parking Management System

## Introduction

The ISPARK Parking Management System is a microservices-based architecture designed to efficiently manage parking spaces. This system automates the management of tariffs, vehicle types, customer profiles, and geographical data. It is built using Docker to encapsulate different services, ensuring scalable and isolated deployment.

## Getting Started

### Prerequisites

- **Docker**: You need Docker installed on your machine to run the containers. Download and install Docker from [Docker's official website](https://www.docker.com/get-started).
- **Docker Compose**: Ensures that you can run multi-container Docker applications. Typically, Docker Desktop includes Docker Compose, but you can verify and install it from [Docker's documentation](https://docs.docker.com/compose/install/).

### Installation

Clone the repository:

```bash
git clone https://github.com/alicancagdas/ispark.git
cd ispark
```



## Start the Services

To build and run all the services defined in the `docker-compose.yml` file, use the following command:

```bash
docker-compose up --build
```

# ISPARK Parking Management System - Services and Access Guide

## React App
**Purpose:** Serves as the front end for the system.  
**Access:** Navigate to [http://localhost:3000](http://localhost:3000) to view the main application interface.

## Tariff Service
**Purpose:** Manages all tariff-related operations.  
**Swagger UI:** Access API documentation and test endpoints at [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/).

## Tariff Operation Service
**Purpose:** Handles specific operations such as updates and deletions for tariffs.  
**Swagger UI:** View and interact with the service at [http://localhost:8081/swagger-ui/index.html#/](http://localhost:8081/swagger-ui/index.html#/).

## Package Creator Service
**Purpose:** Facilitates the creation and management of parking packages.  
**Swagger UI:** Available at [http://localhost:8085/swagger-ui/index.html#/](http://localhost:8085/swagger-ui/index.html#/).

## Customer Type Service
**Purpose:** Manages different customer types within the system.  
**Swagger UI:** API documentation can be accessed at [http://localhost:8082/swagger-ui/index.html#/](http://localhost:8082/swagger-ui/index.html#/).

## Vehicle Type Service
**Purpose:** Oversees the types of vehicles that can be registered in the system.  
**Swagger UI:** Explore APIs at [http://localhost:8083/swagger-ui/index.html#/](http://localhost:8083/swagger-ui/index.html#/).

## Location Service
**Purpose:** Handles data related to geographical locations.  
**Swagger UI:** View and test location-based endpoints at [http://localhost:8084/swagger-ui/index.html#/](http://localhost:8084/swagger-ui/index.html#/).

## Upper Package Service
**Purpose:** Manages upper-tier packages that aggregate several smaller packages.  
**Swagger UI:** Documentation available at [http://localhost:8086/swagger-ui/index.html#/](http://localhost:8086/swagger-ui/index.html#/).

## Monitoring with Prometheus and Grafana
**Prometheus:** Monitor system metrics at [http://localhost:9090](http://localhost:9090).  
**Grafana:** Visualize metrics at [http://localhost:3001](http://localhost:3001).

**MongoDb Express:** Examine Mongo Database  [http://localhost:2727](http://localhost:2727).

## Conclusion
This guide provides all the necessary steps to set up and run the ISPARK Parking Management System. Each service is accessible via its respective port as outlined above, with Swagger UI enabled for interacting with the APIs directly.



## Here it's Turkish Documentation

[readme.pdf](https://github.com/user-attachments/files/17084478/readme.pdf)
