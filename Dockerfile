FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install
COPY /target/crm.jar crm.jar

ENTRYPOINT ["java","-jar", "crm.jar"]