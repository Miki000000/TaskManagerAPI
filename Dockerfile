# Stage 1: Build com JDK
FROM amazoncorretto:21 AS builder

WORKDIR /app

COPY gradle ./gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN ./gradlew dependencies --no-daemon

COPY src ./src
RUN ./gradlew build -x test --no-daemon

# Stage 2: Runtime com JRE mínimo
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]