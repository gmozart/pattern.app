FROM openjdk:11-jdk

COPY target/*.jar  /app-pattern/app-pattern.jar

WORKDIR /app-pattern

EXPOSE 8080

CMD ["sh","-c","java -jar app-pattern.jar"]