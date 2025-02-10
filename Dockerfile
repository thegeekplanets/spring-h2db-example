FROM openjdk:17-jdk-alpine
EXPOSE 8080
ADD target/spring-h2db-example-0.0.1-SNAPSHOT.jar spring-h2db-example.jar
ENTRYPOINT ["java","-jar","/spring-h2db-example.jar"]
