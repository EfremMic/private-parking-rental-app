FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/payment-service-0.0.1-SNAPSHOT.jar ./payment-service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "payment-service.jar"]
