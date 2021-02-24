FROM openjdk:15-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} propostas-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/propostas-0.0.1-SNAPSHOT.jar"]