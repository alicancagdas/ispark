FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

# Maven bağımlılıklarını indiriyoruz ve projeyi derliyoruz.
RUN mvn install -DskipTests

COPY src ./src

# Projeyi derleyelim
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre

COPY --from=build /app/target/api-gateway-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
