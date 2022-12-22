FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/find-info-service-0.0.1-SNAPSHOT.jar findInfoService.jar
ENTRYPOINT ["java", "-jar", "findInfoService.jar"]
