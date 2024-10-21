# Use an official Maven image to build the project
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Debug: list files in target to verify jar exists
RUN ls /app/target

# Use a smaller JDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/user-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]
