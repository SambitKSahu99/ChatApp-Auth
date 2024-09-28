FROM openjdk:17-jdk-slim
ADD build/libs/ChatApp-Auth-0.0.1-SNAPSHOT.jar ChatApp-Auth-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ChatApp-Auth-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082