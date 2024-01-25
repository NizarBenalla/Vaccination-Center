FROM maven:3.9-amazoncorretto-17 AS MAVEN_BUILD
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=MAVEN_BUILD /app/target/ClientVacination-0.0.1-SNAPSHOT.jar /app.jar
CMD ["sh", "-c", "sleep 20 && java -jar /app.jar"]
