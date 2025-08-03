# Train Ticket Booking Using Spring Boot Microservices



This is a Spring Boot application for booking the train tickets. <br/> The app uses Spring Boot Microserices in the backend and React in the front end. <br/> 


This involves several components including: Eureka Service Discovery, API Gateway, User Service, Train Service, Booking Service, Payment Service, Redis caching, Kafka messaging, JWT Security, React Frontend, and Docker containerization. 


## Overview

#### Service discovery: Eureka Server
#### API Gateway: Zuul Gateway forwarding JWT token
#### Services: User, Train, Booking, Payment
#### Persistence: MySQL (or PostgreSQL), Redis cache for session/token
#### Security: JWT based authentication & authorization
#### Messaging: Kafka for async communication
#### Payment: Stripe integration (backend call)
#### Frontend: React + JWT token handling
#### Containerization: Docker for all services



<br/> 


## Important Concurrency Techniques Used:
#### Asynchrony via Kafka: Spreads out load, prevents request burst from overwhelming services.
#### Database ACID Transactions: Safely protects critical seat booking section from race conditions.
#### Thread pools and Reactive clients (optionally) handle multiple requests in parallel.
#### Stateless JWT Auth: No global locks or session blocking, enabling scalability.

<br/> 

This design leverages multi-threading at web server, Kafka consumer, and DB transaction levels combined with asynchronous messaging to ensure the entire system handles very high concurrency safely, avoiding common pitfalls like race conditions or request overload. <br/> 





## Workflow
The workflow of the project is shown below:

<img width="3840" height="1627" alt="Image" src="https://github.com/user-attachments/assets/5ddc05c8-1780-4813-b5c2-8b6877adba06" />
