# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper files
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

# *** THIS IS THE NEW LINE THAT FIXES THE ERROR ***
# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Build the application
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean install -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/complaint-management-0.0.1-SNAPSHOT.jar"]