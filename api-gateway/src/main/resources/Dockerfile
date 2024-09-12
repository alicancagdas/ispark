# Base image olarak Maven'ı kullanıyoruz.
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Çalışma dizinini belirliyoruz.
WORKDIR /app

# Maven bağımlılıklarını optimize etmek için önce pom.xml dosyasını kopyalıyoruz.
COPY pom.xml .

# Maven bağımlılıklarını indiriyoruz.
RUN mvn dependency:go-offline

# Kaynak kodları kopyalıyoruz.
COPY src ./src

# Maven ile projeyi derliyoruz ve testleri atlıyoruz.
RUN mvn package -DskipTests

# Runtime için daha küçük bir JRE image kullanıyoruz.
FROM eclipse-temurin:17-jre

# Derlenmiş JAR dosyasını önceki aşamadan kopyalıyoruz.
COPY --from=build /app/target/api-gateway-0.0.1-SNAPSHOT.jar /app.jar

# Uygulamayı başlatıyoruz.
ENTRYPOINT ["java", "-jar", "/app.jar"]
