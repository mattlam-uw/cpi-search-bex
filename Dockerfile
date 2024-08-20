FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/cpi-search-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the port on which your application runs
EXPOSE 8080