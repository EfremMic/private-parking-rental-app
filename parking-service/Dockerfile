# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Create a directory inside the container for the app
WORKDIR /app

# Copy the built JAR file from the target folder (created by Maven)
COPY target/parking-service-0.0.1-SNAPSHOT.jar /app/parking-service.jar

# Expose the port the service will run on
EXPOSE 8082

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "parking-service.jar"]
