# Build stage
FROM gradle:8.7-jdk17-alpine AS build
WORKDIR /app
# Copy build files first for better layer caching
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
# Copy source code
COPY src ./src
# Build the application (skip tests for faster build)
RUN gradle build -x test --no-daemon

# Run stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copy the built jar file
COPY --from=build /app/build/libs/demo-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
