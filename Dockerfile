# syntax=docker/dockerfile:1.7

FROM gradle:8.5-jdk21 AS build
WORKDIR /workspace

COPY gradlew gradlew
COPY settings.gradle.kts settings.gradle.kts
COPY build.gradle.kts build.gradle.kts
COPY gradle.properties gradle.properties
COPY gradle gradle
COPY buildSrc buildSrc
COPY lambda-core lambda-core

RUN sed -i 's/\r$//' gradlew && chmod +x gradlew
RUN ./gradlew :lambda-core:bootJar --no-daemon

FROM eclipse-temurin:21-jre-jammy
ENV SPRING_PROFILES_ACTIVE=docker
WORKDIR /app

COPY --from=build /workspace/lambda-core/build/libs/lambda-core.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-jar","/app/app.jar"]

