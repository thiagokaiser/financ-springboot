FROM openjdk:11
MAINTAINER Thiago Kaiser
COPY target/financ-1.0.1.jar financ-1.0.1.jar
ENTRYPOINT ["java","-jar","/financ-1.0.1.jar"]