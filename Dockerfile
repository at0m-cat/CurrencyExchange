# Этап 1: Сборка проекта с использованием Maven
FROM maven:4.0.0 AS build

# Копируем файлы проекта в контейнер
COPY . /usr/src/app

# Переходим в директорию проекта
WORKDIR /usr/src/app

# Собираем проект Maven и создаём WAR-файл
RUN mvn clean package

FROM tomcat:jdk21-temurin

COPY target/ROOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
