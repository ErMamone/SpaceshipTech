FROM maven:3.9.8-eclipse-temurin-21 AS build
ENV SPRING_PROFILE=dev

FROM openjdk:21
COPY target/spaceship-0.0.1-SNAPSHOT.jar /opt/lib/app.jar

ENTRYPOINT java -Dspring.profiles.active=$SPRING_PROFILE -jar /opt/lib/app.jar

WORKDIR /opt/lib/app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]