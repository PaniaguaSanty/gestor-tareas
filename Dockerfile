# Dockerfile corregido
# Fase 1: Construcción
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /workspace/app

# Copia archivos necesarios
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Añade permisos de ejecución al mvnw
RUN chmod +x mvnw

# Construye el proyecto
RUN ./mvnw clean package -DskipTests

# Fase 2: Imagen de producción
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia el JAR construido
COPY --from=builder /workspace/app/target/*.jar app.jar

# Puerto expuesto
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]