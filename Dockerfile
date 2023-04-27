FROM bellsoft/liberica-openjdk-alpine-musl:17 as builder
WORKDIR /app
COPY ./pom.xml /app
COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./src ./src
RUN ./mvnw clean package -DskipTests
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/documents-ms-0.0.1-SNAPSHOT.jar .
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "documents-ms-0.0.1-SNAPSHOT.jar"]