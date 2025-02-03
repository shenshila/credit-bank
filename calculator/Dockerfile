FROM openjdk:17-slim as build

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Устанавливаем рабочую директорию
WORKDIR /app
# Копируем локальный Maven репозиторий в контейнер
COPY /C/Users/user/.m2 /root/.m2

# Копируем pom.xml и загружаем зависимости
COPY pom.xml .

# Загружаем зависимости
RUN mvn dependency:go-offline

# Копируем весь исходный код
COPY src /app/src

# Собираем проект с помощью Maven
RUN mvn clean package -DskipTests

# Используем официальный образ с JRE для выполнения Spring Boot приложения
FROM openjdk:17-jre-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный .jar файл из предыдущего этапа
COPY --from=build /target/*.jar calculator.jar

# Открываем порт 8080
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "calculator.jar"]
