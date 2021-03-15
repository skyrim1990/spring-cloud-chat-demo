FROM openjdk:11.0-jre-buster

WORKDIR /project

COPY target/eureka-point-0.1.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "./app.jar"]