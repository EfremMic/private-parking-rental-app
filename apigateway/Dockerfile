FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/apigateway-0.0.1-SNAPSHOT.jar ./apigateway.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "apigateway.jar"]
