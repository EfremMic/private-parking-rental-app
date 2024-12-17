# --- Build Stage ---
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build the JAR file, fail if it doesn't exist
RUN mvn clean install -DskipTests && \
    if [ ! -f /app/target/payment-service-0.0.1-SNAPSHOT.jar ]; then echo "JAR file not found"; exit 1; fi

# --- Production Stage ---
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar ./payment-service.jar

# Expose port 8084
EXPOSE 8084

# Set the entrypoint to run the app
ENTRYPOINT ["java", "-jar", "payment-service.jar"]
