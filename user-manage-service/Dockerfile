FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/user-manage-service-0.0.1-SNAPSHOT.jar manageService.jar
ENTRYPOINT ["java", "-jar", "manageService.jar"]
