# ---- Build ----
FROM maven:3.10-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn -B package -DskipTests

# ---- Runtime ----
FROM eclipse-temurin:25-jre
WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring spring

COPY --from=build /app/target/JobPortal-*.jar app.jar

USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
