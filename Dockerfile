#
# Build stage
#
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /home/app
COPY pom.xml /home/app
COPY src /home/app/src
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM eclipse-temurin:21-jre AS runtime
COPY --from=build /home/app/target/financ-1.0.0.jar /usr/local/lib/financ.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/usr/local/lib/financ.jar"]
