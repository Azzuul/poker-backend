FROM maven:3.9-eclipse-temurin-17 AS build

LABEL maintainer="bennu <contacto@bennu.cl>"

COPY ./src /usr/src/app/src
COPY ./pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml package


FROM eclipse-temurin:17

COPY --from=build /usr/src/app/target/poker-card.jar /usr/src/app/poker-card.jar

EXPOSE 8080
ENTRYPOINT ["java" , "-jar", "/usr/src/app/poker-card.jar"]



