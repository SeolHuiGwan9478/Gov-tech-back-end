FROM openjdk:17-jdk-slim
WORKDIR /chat/app
COPY build/libs/gov-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "./app.jar"]