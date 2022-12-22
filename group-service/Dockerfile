FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/group-service-0.0.1-SNAPSHOT.jar groupService.jar
ENTRYPOINT ["java", "-jar", "groupService.jar"]
