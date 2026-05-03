# Secrets requeridos en GitHub Actions

Ve a: **Settings → Secrets and variables → Actions → New repository secret**

## Obligatorios (CI/CD completo)

| Secret | Valor | Cuándo se usa |
|--------|-------|---------------|
| `SONAR_TOKEN` | Token generado en SonarCloud → My Account → Security | Job `test-backend` |
| `DEPLOY_HOST` | IP o dominio del servidor de staging | Job `deploy` |
| `DEPLOY_USER` | Usuario SSH del servidor (ej: `ubuntu`) | Job `deploy` |
| `DEPLOY_SSH_KEY` | Contenido completo de la clave privada SSH (`~/.ssh/id_rsa`) | Job `deploy` |

## Automáticos (no necesitas crearlos)

| Secret | Descripción |
|--------|-------------|
| `GITHUB_TOKEN` | Lo provee GitHub automáticamente en cada ejecución |

## Cómo obtener el SONAR_TOKEN

1. Entra a https://sonarcloud.io
2. Click en tu avatar → **My Account**
3. Tab **Security**
4. En "Generate Tokens" → escribe `github-actions` → **Generate**
5. Copia el token y pégalo como secret `SONAR_TOKEN` en GitHub

## Nota sobre el job deploy

Si aún no tienes servidor de staging, el job `deploy` fallará pero los demás
jobs (tests + sonar + build) funcionarán correctamente. Puedes comentar
el job `deploy` en el YAML hasta tener el servidor listo.
