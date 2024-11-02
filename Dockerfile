FROM openjdk:17-jdk-slim

COPY feedback-service-standalone.jar .

ENTRYPOINT ["java", "-jar", "feedback-service-standalone.jar"]