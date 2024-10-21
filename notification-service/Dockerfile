# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Create a directory inside the container for the app
WORKDIR /app

# Copy the built JAR file from the target folder (created by Maven)
COPY target/notification-service-0.0.1-SNAPSHOT.jar /app/notification-service.jar

# Expose the port the service will run on
EXPOSE 8083

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
