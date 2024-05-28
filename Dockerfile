FROM maven:3.9.6-amazoncorretto-21 as builder
ENV TEST "yo"
USER docker
ENV MAVEN_TARGET "clean verify"
CMD mvn ${MAVEN_TARGET}

FROM amazoncorretto:21-alpine-jdk
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
