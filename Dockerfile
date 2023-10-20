# Use a base image with Maven and OpenJDK
FROM maven:3.6.3-jdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Build the Maven project, which will package the Spring Boot application into a JAR file
RUN mvn clean package

# Use a lighter base image for running the application
FROM openjdk

# Set the working directory for the runtime container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime container
COPY --from=build /app/target/springrestapi-0.0.1-SNAPSHOT.jar . 

# Expose the port the Spring Boot application will run on
EXPOSE 8080

# Command to run the application when the container starts
CMD ["java", "-jar", "springrestapi-0.0.1-SNAPSHOT.jar"] 
