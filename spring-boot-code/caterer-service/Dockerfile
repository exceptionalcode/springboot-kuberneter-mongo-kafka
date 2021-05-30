# Start with a base image containing Java runtime
FROM adoptopenjdk/openjdk11:alpine-jre

# Add Maintainer Info
MAINTAINER Ishaan Solanki <ishaansolanki6@gmail.com>

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8088 available to the world outside this container
EXPOSE 8088

# The application's jar file
ARG JAR_FILE=target/caterer-service-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} caterer-service.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/caterer-service.jar"]