FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the pre-compiled JAR file
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set active profile to production
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
