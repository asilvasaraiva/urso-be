FROM openjdk:11

ENV urso.app.jwtSecret=MySecretUrSODecoderSecretKey

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 8080