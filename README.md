Private Parking Rental App
The Private Parking Rental App is a microservices-based application designed to manage users, parking spots, notifications, and payments. This project demonstrates key microservices principles such as modularity, scalability, and effective communication between services.

Table of Contents
Overview
Technologies Used
Setup and Installation
API Documentation
User API
Parking API
Payment API
Notification API
System Architecture
Health Checks and Monitoring
Lessons Learned
Overview
This application enables users to:

Manage user profiles: Register, authenticate, and update user information.
Handle parking spots: List, book, and manage parking spaces.
Send notifications: Keep users updated via email or SMS notifications.
Process payments: Enable secure payment transactions for parking reservations.
Key features include:

Microservice architecture for modularity and scalability.
Inter-service communication using REST APIs and asynchronous messaging (RabbitMQ).
Containerized deployment using Docker for consistency across environments.
Technologies Used
Java 17: Programming language.
Spring Boot: Framework for building microservices.
Consul: Service discovery and configuration management.
RabbitMQ: Asynchronous messaging for event-driven communication.
Docker: Containerization for consistent deployments.
H2 Database: In-memory database for development and testing.
Stripe API: Payment processing.
Spring Boot Actuator: Health checks and monitoring.
Setup and Installation
Prerequisites
Java 17 or later
Docker and Docker Compose
Maven for building the project
Steps to Run the Application
Clone the repository:

bash
copier code
git clone https://github.com/EfremMic/private-parking-rental-app.git
cd private-parking-rental-app
Build the project:

bash
Copier kode
mvn clean install
Start the services using Docker Compose:

bash
copier kode
docker-compose up --build
Access the services:

Consul Dashboard: http://localhost:8500
RabbitMQ Dashboard: http://localhost:15672
API Gateway: http://localhost:8000
API Documentation
User API
GET /api/users/me: Fetch or create a user based on OpenID Connect authentication.
POST /api/users/create: Create a user with JSON data.
POST /api/users/createWithParams: Create a user using URL parameters.
Parking API
POST /api/parking/add: Add a new parking spot.
GET /api/parking/{id}: Retrieve a parking spot by its ID.
GET /api/parking/list: List all available parking spots.
GET /api/parking/user/{userId}/list: List parking spots owned by a specific user.
Payment API
GET /api/payments/health: Check the health status of the payment service.
POST /api/payments/charge: Process a payment for a parking reservation.
POST /api/payments: Create a PaymentIntent for client-side payment processing.
Notification API
GET /api/notifications/list: Retrieve a list of all notifications in the system.
System Architecture
The system uses a microservice architecture with the following services:

User Service: Manages user profiles and authentication.
Parking Service: Handles parking spot management.
Payment Service: Processes payments via Stripe.
Notification Service: Sends email and SMS notifications.
API Gateway: Routes all incoming requests to the correct service.
Consul: Manages service discovery and health checks.
RabbitMQ: Handles asynchronous messaging between services.
Communication Patterns
Synchronous communication: REST APIs between services.
Asynchronous communication: RabbitMQ for event-driven messaging.
Health Checks and Monitoring
Consul: Monitors the health of all services and enables service discovery.
Spring Boot Actuator: Provides endpoints like /actuator/health for checking individual service statuses.
RabbitMQ Admin Dashboard: Tracks the status of message queues and exchanges.
Lessons Learned
During the development of this project, the following key lessons were learned:

Configuration Management: Properly managing configurations for different services is critical for reliability.
Debugging Distributed Systems: Tools like Docker logs, RabbitMQ dashboards, and Actuator endpoints were invaluable.
Asynchronous Messaging: Implementing RabbitMQ improved scalability but required a deeper understanding of AMQP protocols.
Containerization: Docker provided consistency across environments but introduced challenges with networking between services.