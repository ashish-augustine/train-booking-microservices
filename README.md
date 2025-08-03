# Train Ticket Booking Using Spring Boot Microservices



This is a Spring Boot application for booking the train tickets. <br/> The app uses Spring Boot Microserices in the backend and React in the front end. <br/> 


This involves several components including: Eureka Service Discovery, API Gateway, User Service, Train Service, Booking Service, Payment Service, Redis caching, Kafka messaging, JWT Security, React Frontend, and Docker containerization. 
<br/> 

This design leverages multi-threading at web server, Kafka consumer, and DB transaction levels combined with asynchronous messaging to ensure the entire system handles very high concurrency safely, avoiding common pitfalls like race conditions or request overload. <br/> 



## Overview

  Service discovery: Eureka Server <br/> 
 API Gateway: Zuul Gateway forwarding JWT token <br/> 
 Services: User, Train, Booking, Payment <br/> 
  Persistence: MySQL (or PostgreSQL), Redis cache for session/token <br/> 
 Security: JWT based authentication & authorization <br/> 
 Messaging: Kafka for async communication <br/> 
  Payment: Stripe integration (backend call) <br/> 
 Frontend: React + JWT token handling <br/> 
  Containerization: Docker for all services <br/> 



<br/> 


## Important Concurrency Techniques Used: 
 Asynchrony via Kafka: Spreads out load, prevents request burst from overwhelming services.  <br/> 
 Database ACID Transactions: Safely protects critical seat booking section from race conditions. <br/> 
  Thread pools and Reactive clients (optionally) handle multiple requests in parallel. <br/> 
 Stateless JWT Auth: No global locks or session blocking, enabling scalability. <br/> 





## Workflow 

<img width="3840" height="1627" alt="Image" src="https://github.com/user-attachments/assets/5ddc05c8-1780-4813-b5c2-8b6877adba06" />



 


## How to run this project
---

REQUIREMENTS:
- Java 11+ and Maven installed
- Node.js + npm installed
- Docker & Docker Compose installed (recommended)
- Stripe Account + API Keys (for Payment Service)

---

1. START INFRASTRUCTURE SERVICES (using Docker):

Run these commands to start required infrastructure components:

a) Start Zookeeper:
   docker run -d --name zookeeper -p 2181:2181 zookeeper

b) Start Kafka Broker:
   docker run -d --name kafka -p 9092:9092 \
     -e KAFKA_ZOOKEEPER_CONNECT=host.docker.internal:2181 \
     -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
     wurstmeister/kafka

Note: Replace 'host.docker.internal' with 'localhost' on Linux.

c) Start Redis:
   docker run -d --name redis -p 6379:6379 redis

d) Start MySQL databases (for User, Train, Booking services):

   docker run -d --name mysql-user -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=userdb mysql

   docker run -d --name mysql-train -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=traindb mysql

   docker run -d --name mysql-booking -p 3308:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bookingdb mysql

---

2. CONFIGURE MICROSERVICE APPLICATIONS:

- Update `application.yml` or `application.properties` in each service with correct:
  - Database URLs, usernames, passwords
  - Kafka bootstrap servers (localhost:9092)
  - Redis host (localhost)
  - Eureka server URL (http://localhost:8761/eureka)
  - Stripe API credentials in Payment Service

- Ensure JWT secret keys are properly set in User Service and propagated securely.

---

3. BUILD MICROSERVICES (execute in each service folder):

   mvnw clean package

or (if Maven installed globally):

   mvn clean package

This creates `target/*.jar` executable files.

---

4. RUN MICROSERVICES:

For each service (user-service, train-service, booking-service, payment-service, api-gateway, eureka-server), run:

   java -jar target/<service>.jar

Typical port assignments (verify in config files):

- Eureka Server: 8761
- API Gateway: 8080
- User Service: 8081
- Train Service: 8082
- Booking Service: 8083
- Payment Service: 8084

---

5. VERIFY SERVICES:

- Access Eureka Dashboard:
  http://localhost:8761

- Confirm all microservices are registered & UP.

---

6. RUN REACT FRONTEND:

From the frontend directory
