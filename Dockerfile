# Dockerfile
# Fase 1: Construcción
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /workspace/app

# Copia solo los archivos necesarios para construir
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Construye el proyecto
RUN ./mvnw clean package -DskipTests

# Fase 2: Imagen final
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia el JAR desde la fase de construcción
COPY --from=builder /workspace/app/target/*.jar app.jar

# Puerto expuesto
EXPOSE 8080

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]