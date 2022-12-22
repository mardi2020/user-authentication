FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/discoveryServer-0.0.1-SNAPSHOT.jar discoveryService.jar
ENTRYPOINT ["java", "-jar", "discoveryService.jar"]