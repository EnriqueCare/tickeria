# Etapa 1: Construcción del JAR usando Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar el pom.xml y descargar las dependencias para cachear este paso
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar el proyecto saltando los tests para agilizar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Entorno de ejecución ligero
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiar el archivo JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto del backend
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]