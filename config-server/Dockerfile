FROM openjdk:17-jdk-slim
COPY target/*.jar config-server-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/config-server-1.0-SNAPSHOT.jar"]
