FROM openjdk:17-jdk-slim
COPY target/*.jar api-gateway-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/api-gateway-1.0-SNAPSHOT.jar"]
