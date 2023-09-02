FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifactRUN mvn package -DskipTests
RUN mvn package -DskipTests

#Run the web service on container startup.
CMD ["java","-jar","/user-center-backend/target/user-center-backend-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]