FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY /build/libs/core-settings-0.0.1-SNAPSHOT.jar app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "app.jar"]
