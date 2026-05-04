# FlyTrack — AeroPuerto Smart

Proyecto académico de la práctica DevOps para **Ingeniería de Software III**  
Universidad del Quindío · Programa de Ingeniería de Sistemas y Computación

---

## Estructura del proyecto

```
aeropuerto-smart/
├── flytrack-api/          # Backend Spring Boot 3 + Gradle + Java 17
├── flytrack-front/        # Frontend Angular 17 (standalone components)
├── docker-compose.yml     # Orquestación local completa
├── .env.example           # Plantilla de variables de entorno (se sube al repo)
├── .env                   # Variables reales (NO se sube al repo — ver .gitignore)
└── .github/workflows/     # Pipeline CI/CD
```

---

## Tecnologías

| Capa          | Tecnología                       |
|---------------|----------------------------------|
| Backend       | Spring Boot 3.2, Java 17, Gradle |
| Frontend      | Angular 17 (standalone)          |
| Base de datos | PostgreSQL 16                    |
| Contenedores  | Docker + Docker Compose          |
| Servidor web  | Nginx 1.25                       |
| CI/CD         | GitHub Actions                   |
| Calidad       | SonarCloud + JaCoCo              |
| Despliegue    | Render                           |

---

## Entornos desplegados

| Servicio  | URL                                        |
|-----------|--------------------------------------------|
| Frontend  | https://flytrack-front.onrender.com        |
| Backend   | https://flytrack-api.onrender.com/api      |

> El plan Free de Render duerme los servicios tras 15 minutos de inactividad.
> El primer request puede tardar hasta 30 segundos mientras el servicio se despierta.

---

## Pipeline CI/CD

El pipeline se ejecuta automáticamente en cada push o Pull Request a la rama `main`.

```
push a main
      │
      ├── Test Backend (Java 17)   ──┐
      │                              ├── Build & Push Docker Images ── Deploy a Render
      └── Test Frontend (Angular 17)─┘
```

### Jobs

| Job | Qué hace | Cuándo corre |
|-----|----------|--------------|
| Test Backend | Ejecuta tests JUnit, genera reporte JaCoCo y analiza con SonarCloud | Siempre |
| Test Frontend | Ejecuta tests Karma con ChromeHeadless y verifica build de producción | Siempre |
| Build & Push Docker Images | Construye imágenes Docker y las publica en GitHub Container Registry | Solo en push a main |
| Deploy a Render | Dispara redeploy del backend y frontend en Render via Deploy Hooks | Solo en push a main |

### Secrets requeridos en GitHub Actions

Configurar en: **Settings → Secrets and variables → Actions**

| Secret | Descripción |
|--------|-------------|
| `SONAR_TOKEN` | Token de autenticación de SonarCloud |
| `RENDER_DEPLOY_HOOK_API` | Deploy Hook del servicio flytrack-api en Render |
| `RENDER_DEPLOY_HOOK_FRONT` | Deploy Hook del servicio flytrack-front en Render |
| `GITHUB_TOKEN` | Generado automáticamente por GitHub Actions |

---

## Análisis de calidad — SonarCloud

El análisis de calidad se ejecuta automáticamente en cada pipeline.  
Dashboard del proyecto: https://sonarcloud.io/organizations/jeronimoguerreromaya/projects

Métricas analizadas:
- Cobertura de código (JaCoCo)
- Code smells y duplicaciones
- Vulnerabilidades y bugs

---

## Variables de entorno

El proyecto usa un archivo `.env` para gestionar credenciales y configuración.  
**Nunca subas el archivo `.env` al repositorio.**

### Configuración inicial

```bash
# 1. Copia la plantilla
cp .env.example .env

# 2. Edita .env con tus valores reales
nano .env   # o usa tu editor favorito
```

### Variables disponibles

| Variable            | Descripción                              | Valor por defecto                               |
|---------------------|------------------------------------------|-------------------------------------------------|
| `POSTGRES_DB`       | Nombre de la base de datos               | `flytrackdb`                                    |
| `POSTGRES_USER`     | Usuario de PostgreSQL                    | `flytrack`                                      |
| `POSTGRES_PASSWORD` | Contraseña de PostgreSQL                 | *(definir en .env)*                             |
| `DB_URL`            | URL JDBC de conexión                     | `jdbc:postgresql://flytrack-db:5432/flytrackdb` |
| `DB_USER`           | Usuario que usa Spring Boot              | `flytrack`                                      |
| `DB_PASS`           | Contraseña que usa Spring Boot           | *(definir en .env)*                             |
| `SERVER_PORT`       | Puerto del backend                       | `8080`                                          |
| `FRONTEND_PORT`     | Puerto del frontend                      | `80`                                            |

> El archivo `.env.example` contiene la estructura con valores de ejemplo y **sí se sube al repositorio**.  
> El archivo `.env` con los valores reales está en `.gitignore` y **nunca debe commitearse**.

---

## Levantar el proyecto localmente

### Opción 1 — Docker Compose (recomendado)

```bash
# Desde la carpeta aeropuerto-smart/
cp .env.example .env      # solo la primera vez
docker compose up --build
```

Servicios disponibles:
- Frontend: http://localhost:80
- API REST:  http://localhost:8080/api
- PostgreSQL: localhost:5432

### Opción 2 — Desarrollo local

**Backend:**
```bash
cd flytrack-api
./gradlew bootRun
# API disponible en http://localhost:8080
```

**Frontend:**
```bash
cd flytrack-front
npm install
npm start
# App disponible en http://localhost:4200
```

> Para desarrollo local necesitas PostgreSQL corriendo en el puerto 5432  
> con los valores definidos en tu `.env`.

---

## Pruebas unitarias

### Backend (JUnit 5 + Mockito)

```bash
cd flytrack-api
./gradlew test
```

Los reportes quedan en `flytrack-api/build/reports/tests/test/`.  
El reporte de cobertura JaCoCo queda en `flytrack-api/build/reports/jacoco/`.

Clases cubiertas:
- `VueloService` — 6 tests
- `ReporteEquipajeService` — 3 tests

### Frontend (Karma + Jasmine)

```bash
cd flytrack-front
npm test
```

Clases cubiertas:
- `AppComponent` — 3 tests
- `VueloService` — 4 tests
- `NotificacionService` — 3 tests
- `ReporteEquipajeService` — 3 tests

---

## Endpoints REST

### Vuelos

| Método | Ruta                        | Descripción                |
|--------|-----------------------------|----------------------------|
| GET    | /api/vuelos                 | Listar todos los vuelos    |
| GET    | /api/vuelos/{id}            | Obtener vuelo por ID       |
| GET    | /api/vuelos/numero/{numero} | Obtener vuelo por número   |
| POST   | /api/vuelos                 | Crear nuevo vuelo          |
| PATCH  | /api/vuelos/{id}/estado     | Actualizar estado y puerta |
| DELETE | /api/vuelos/{id}            | Eliminar vuelo             |

**Ejemplo — Crear vuelo:**
```json
POST /api/vuelos
{
  "numeroVuelo": "AV205",
  "origen": "Bogotá",
  "destino": "Cali",
  "puertaEmbarque": "B2",
  "estado": "PROGRAMADO"
}
```

**Ejemplo — Cambiar estado:**
```json
PATCH /api/vuelos/1/estado
{
  "estado": "RETRASADO",
  "puertaEmbarque": "C5"
}
```

Estados válidos: `PROGRAMADO`, `RETRASADO`, `CANCELADO`, `EMBARCANDO`

### Reportes de Equipaje

| Método | Ruta                        | Descripción                   |
|--------|-----------------------------|-------------------------------|
| GET    | /api/reportes               | Listar todos los reportes     |
| POST   | /api/reportes               | Crear reporte de equipaje     |
| PATCH  | /api/reportes/{id}/estado   | Actualizar estado del reclamo |

### Notificaciones

| Método | Ruta                           | Descripción                     |
|--------|--------------------------------|---------------------------------|
| GET    | /api/notificaciones            | Listar todas las notificaciones |
| GET    | /api/notificaciones/vuelo/{id} | Notificaciones de un vuelo      |

---

## Funcionalidades de FlyTrack

- **Itinerarios**: tarjetas por vuelo con colores por estado, buscador por número/ciudad
- **Notificaciones**: alertas automáticas al cambiar estado, marcar como leída individualmente o todas
- **Reportar equipaje**: formulario con validación en tiempo real, modal de confirmación con número de radicado
