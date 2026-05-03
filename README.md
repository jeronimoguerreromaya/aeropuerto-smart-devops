# ✈️ FlyTrack — AeroPuerto Smart

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
└── .github/workflows/     # Pipeline CI/CD (Fase 2 del laboratorio)
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

| Variable            | Descripción                              | Valor por defecto                              |
|---------------------|------------------------------------------|------------------------------------------------|
| `POSTGRES_DB`       | Nombre de la base de datos               | `flytrackdb`                                   |
| `POSTGRES_USER`     | Usuario de PostgreSQL                    | `flytrack`                                     |
| `POSTGRES_PASSWORD` | Contraseña de PostgreSQL                 | *(definir en .env)*                            |
| `DB_URL`            | URL JDBC de conexión                     | `jdbc:postgresql://flytrack-db:5432/flytrackdb`|
| `DB_USER`           | Usuario que usa Spring Boot              | `flytrack`                                     |
| `DB_PASS`           | Contraseña que usa Spring Boot           | *(definir en .env)*                            |
| `SERVER_PORT`       | Puerto del backend                       | `8080`                                         |
| `FRONTEND_PORT`     | Puerto del frontend                      | `80`                                           |

> El archivo `.env.example` contiene la estructura con valores de ejemplo y **sí se sube al repositorio** para que otros desarrolladores sepan qué variables configurar.  
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

## Endpoints REST

### Vuelos
| Método | Ruta                        | Descripción                    |
|--------|-----------------------------|--------------------------------|
| GET    | /api/vuelos                 | Listar todos los vuelos        |
| GET    | /api/vuelos/{id}            | Obtener vuelo por ID           |
| POST   | /api/vuelos                 | Crear nuevo vuelo              |
| PATCH  | /api/vuelos/{id}/estado     | Actualizar estado y puerta     |
| DELETE | /api/vuelos/{id}            | Eliminar vuelo                 |

### Reportes de Equipaje
| Método | Ruta                        | Descripción                    |
|--------|-----------------------------|--------------------------------|
| GET    | /api/reportes               | Listar todos los reportes      |
| POST   | /api/reportes               | Crear reporte de equipaje      |
| PATCH  | /api/reportes/{id}/estado   | Actualizar estado del reclamo  |

### Notificaciones
| Método | Ruta                           | Descripción                     |
|--------|--------------------------------|---------------------------------|
| GET    | /api/notificaciones            | Listar todas las notificaciones |
| GET    | /api/notificaciones/vuelo/{id} | Notificaciones de un vuelo      |

---

## Pruebas unitarias

```bash
cd flytrack-api
./gradlew test
```

Los reportes quedan en `flytrack-api/build/reports/tests/`.

---

## Funcionalidades de FlyTrack

- **Itinerarios**: tarjetas por vuelo con colores por estado, buscador por número/ciudad
- **Notificaciones**: alertas automáticas al cambiar estado, marcar como leída individualmente o todas
- **Reportar equipaje**: formulario con validación en tiempo real, modal de confirmación con número de radicado
