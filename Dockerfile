# Step 1: Build the application using Maven and Java 21
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the application using Java 21 Runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Force Spring Boot to use the port provided by Render
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]