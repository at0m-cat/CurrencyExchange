FROM maven:3.8.7-eclipse-temurin-17 AS build

RUN mvn clean package

FROM tomcat:jdk21-temurin

COPY target/ROOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
