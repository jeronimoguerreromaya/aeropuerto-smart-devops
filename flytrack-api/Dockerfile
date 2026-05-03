# ---- Etapa 1: Build con imagen oficial de Gradle ----
# Usa la imagen que ya trae Gradle + JDK 17, sin necesitar el wrapper local
FROM gradle:8.7-jdk17-alpine AS builder

WORKDIR /app

# Copiar archivos de configuracion primero (cache de dependencias)
COPY build.gradle settings.gradle ./

# Descargar dependencias (esta capa se cachea si build.gradle no cambia)
RUN gradle dependencies --no-daemon || true

# Copiar el codigo fuente y compilar
COPY src ./src
RUN gradle clean bootJar --no-daemon

# ---- Etapa 2: Runtime ligero (solo JRE) ----
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# Usuario no-root por seguridad
RUN addgroup -S flytrack && adduser -S flytrack -G flytrack
USER flytrack

# Copiar solo el JAR generado desde la etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
