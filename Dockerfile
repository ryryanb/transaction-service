# --------- Stage 1: Build the JAR ----------
FROM openjdk:17-jdk-slim AS build

# Install Maven
RUN apt-get update && apt-get install -y maven
WORKDIR /app

# Copy your Maven project files
COPY pom.xml .
COPY src ./src

# Package the application (without running tests, optional)
RUN mvn clean package -DskipTests

# --------- Stage 2: Run the JAR ----------
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set the entrypoint for the container to run the jar file with JVM arguments
ENTRYPOINT ["java", "-jar", "app.jar"]

# Pass the JVM arguments in the CMD (so you can modify them if needed)
CMD ["--add-opens", "java.base/java.net=ALL-UNNAMED"]