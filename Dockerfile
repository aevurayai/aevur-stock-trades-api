FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY pom.xml ./
COPY git-hooks ./git-hooks
COPY src ./src
COPY CONTRACT.md ./CONTRACT.md
COPY README.md ./README.md
COPY APPROACH.md ./APPROACH.md
COPY SYSTEM.md ./SYSTEM.md

RUN mvn -DskipTests clean package

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /workspace/target/stocktrade-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
