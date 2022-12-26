FROM openjdk:17-ea-11-jdk-slim as build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY src src

RUN ./gradlew bootJar
RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../*.jar)


FROM openjdk:11-jre-slim-buster
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF

ENTRYPOINT ["java", "-cp","app:app/lib/*", "com.mardi2020.discoveryserver.EurekaDiscoveryApplication"]
