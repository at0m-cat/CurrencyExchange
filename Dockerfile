
# Собираем проект Maven и создаём WAR-файл
RUN mvn clean package

FROM tomcat:jdk21-temurin

COPY target/ROOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
