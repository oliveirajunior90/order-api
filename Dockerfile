FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src
COPY commerce-proto ./commerce-proto

RUN mvn clean package

FROM openjdk:17-jdk-alpine

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]