FROM openjdk:8-jdk-alpine
VOLUME /tmp
# ARG JAR_FILE
# ADD ${JAR_FILE} app.jar
ADD /target/yahtzee-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]