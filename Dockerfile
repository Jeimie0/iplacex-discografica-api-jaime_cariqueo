# Stage 1: compilar la API REST con Gradle y JDK 21
FROM gradle:jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootWar -x test --no-daemon

# Stage 2: ejecutar el WAR con OpenJDK 21
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/discografia-1.war app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]
