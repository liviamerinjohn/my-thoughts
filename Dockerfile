# Step 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
# This picks up the jar file built in the previous step
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# The -Dserver.port part ensures Spring Boot honors the port Render gives it
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]