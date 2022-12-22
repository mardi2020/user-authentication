FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/apiGateway-0.0.1-SNAPSHOT.jar apiGateway.jar
ENTRYPOINT ["java", "-jar", "apiGateway.jar"]