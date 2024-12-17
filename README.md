
# Private Parking Rental App

The **Private Parking Rental App** is a microservices-based application designed to manage user accounts, parking reservations, notifications, and payments. It demonstrates best practices in microservices architecture, including modular design, scalability, and asynchronous communication.

---

## **Table of Contents**
1. [Project Overview](#project-overview)
2. [Technologies Used](#technologies-used)
3. [Setup and Installation](#setup-and-installation)
4. [API Documentation](#api-documentation)
    - [User API](#user-api)
    - [Parking API](#parking-api)
    - [Payment API](#payment-api)
    - [Notification API](#notification-api)
5. [System Architecture](#system-architecture)
6. [Health Checks and Monitoring](#health-checks-and-monitoring)
7. [Lessons Learned](#lessons-learned)

---

## **Project Overview**

This application provides the following features:
- **User Management**: Register, authenticate, and manage user profiles.
- **Parking Spot Management**: Add, list, and manage parking reservations.
- **Notifications**: Notify users via email or SMS for reservations and payments.
- **Payments**: Securely process payments for parking reservations using Stripe.

---

## **Technologies Used**

- **Java 17**: Programming language.
- **Spring Boot**: Framework for building microservices.
- **Consul**: Service discovery and configuration management.
- **RabbitMQ**: Asynchronous messaging.
- **Docker**: Containerization for deployment consistency.
- **H2 Database**: In-memory database for development and testing.
- **Stripe API**: Handles secure payments.
- **Spring Boot Actuator**: Provides health check endpoints and service monitoring.

---

## **Setup and Installation**

### Prerequisites
- **Java 17** or later
- **Docker** and **Docker Compose**
- **Maven** for building the project

### Steps to Run the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/EfremMic/private-parking-rental-app.git
   cd private-parking-rental-app
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Start the services using Docker Compose:
   ```bash
   docker-compose up --build
   ```

4. Access the services:
    - **Consul Dashboard**: [http://localhost:8500](http://localhost:8500)
    - **RabbitMQ Dashboard**: [http://localhost:15672](http://localhost:15672)
    - **API Gateway**: [http://localhost:8000](http://localhost:8000)

---

## **API Documentation**

### **User API**
- **`GET /api/users/me`**: Fetch or create a user based on OpenID Connect authentication.
- **`POST /api/users/create`**: Create a user with JSON data.
- **`POST /api/users/createWithParams`**: Create a user using URL parameters.

### **Parking API**
- **`POST /api/parking/add`**: Add a new parking spot.
- **`GET /api/parking/{id}`**: Retrieve a parking spot by its ID.
- **`GET /api/parking/list`**: List all parking spots.
- **`GET /api/parking/user/{userId}/list`**: List parking spots owned by a specific user.

### **Payment API**
- **`GET /api/payments/health`**: Check the health status of the payment service.
- **`POST /api/payments/charge`**: Process a payment for a parking reservation.
- **`POST /api/payments`**: Create a PaymentIntent for client-side payment processing.

### **Notification API**
- **`GET /api/notifications/list`**: Retrieve a list of all notifications in the system.

---

## **System Architecture**

The system is built with a microservice architecture, consisting of:
- **User Service**: Manages user profiles and authentication.
- **Parking Service**: Handles parking spot management.
- **Payment Service**: Processes payments using Stripe.
- **Notification Service**: Sends email and SMS notifications.
- **API Gateway**: Routes incoming requests to the appropriate service.
- **Consul**: Manages service discovery and health checks.
- **RabbitMQ**: Enables asynchronous communication between services.

### **Communication Patterns**
- **Synchronous**: REST APIs between services.
- **Asynchronous**: RabbitMQ for event-driven messaging.

---

## **Health Checks and Monitoring**

- **Consul**: Provides centralized health checks for all services.
- **Spring Boot Actuator**: Offers `/actuator/health` endpoints for each service.
- **RabbitMQ Dashboard**: Monitors queues and message delivery.

---

## **Lessons Learned**

This project provided hands-on experience with:
1. **Microservices Design**: Implementing modular, scalable systems.
2. **Configuration Management**: Managing settings for distributed services using Consul.
3. **Asynchronous Messaging**: Using RabbitMQ for decoupled communication.
4. **Debugging Distributed Systems**: Utilizing tools like Docker logs, RabbitMQ dashboards, and health check endpoints for troubleshooting.

---

## **Contributions**
# This project was a result of close collaboration between both team members. We worked together on all components, sharing responsibilities for design, implementation, debugging, and integration. Our teamwork ensured consistency and quality across the entire system.

# Key areas of collaboration included:

1. User Service: Handling user registration, authentication, and management.
2. Parking Service: Managing parking spot listings and reservations.
3. Notification Service: Sending email/SMS notifications to users.
4. Payment Service: Processing payments securely via Stripe.
5. API Gateway: Routing requests and ensuring seamless communication between services.

| Team Member     | Role                     | Key Contributions |
|-----------------|--------------------------|--|
| Efrem           | Developer                | Worked collaboratively on all components. |
| Saro            | Developer                | Worked collaboratively on all components. |


