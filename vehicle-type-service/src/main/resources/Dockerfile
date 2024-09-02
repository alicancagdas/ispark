# Base image using Maven for compiling the Java project
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Set the working directory in the Docker image
WORKDIR /app

# Copy only the pom.xml initially to leverage Docker cache layers
COPY pom.xml .

# Download all required Maven dependencies (cache this layer)
RUN mvn dependency:go-offline

# Copy the source code of the project into the Docker image
COPY src ./src

# Package the application skipping unit tests to speed up the build process
RUN mvn package -DskipTests

# Use a smaller JRE image for running the application
FROM eclipse-temurin:17-jre

# Copy the compiled jar file from the build stage into the JRE image
COPY --from=build /app/target/vehicle-type-service-0.0.1-SNAPSHOT.jar /app.jar

# Define the entry point which runs the Java application
ENTRYPOINT ["java", "-jar", "/app.jar"]
