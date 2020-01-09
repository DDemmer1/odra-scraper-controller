FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY target/odra-scraper-controller.jar app.jar
ENTRYPOINT ["java","-jar" ,"-Dspring.profiles.active=production","/app.jar"]