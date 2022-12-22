FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/join-service-0.0.1-SNAPSHOT.jar joinService.jar
ENTRYPOINT ["java", "-jar", "joinService.jar"]
