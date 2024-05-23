FROM maven:3.9-eclipse-temurin-17 AS build


COPY ./src /usr/src/app/src
COPY ./pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml package

# Etapa final
FROM eclipse-temurin:17

COPY --from=build /usr/src/app/target/poker-game.jar /usr/src/app/poker-game.jar

EXPOSE 8081
ENTRYPOINT ["java" , "-jar", "/usr/src/app/poker-game.jar"]

