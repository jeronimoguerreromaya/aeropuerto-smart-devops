# ---- Etapa 1: Build Angular ----
FROM node:20-alpine AS builder

WORKDIR /app

# Copiar manifiestos primero para aprovechar cache de Docker
COPY package*.json ./
RUN npm install --silent

# Copiar el resto del codigo y compilar para produccion
COPY . .
RUN npm run build:prod

# ---- Etapa 2: Servir con Nginx ----
FROM nginx:1.25-alpine AS runtime

# Eliminar configuracion por defecto de Nginx
RUN rm /etc/nginx/conf.d/default.conf

# Copiar template de configuracion (usa $PORT via envsubst)
COPY nginx.conf /etc/nginx/templates/default.conf.template

# Copiar el build de Angular
COPY --from=builder /app/dist/flytrack-front/browser /usr/share/nginx/html

EXPOSE 10000

# La imagen oficial de nginx procesa automáticamente /etc/nginx/templates/
# con envsubst antes de arrancar, reemplazando $PORT con el valor real
CMD ["/bin/sh", "-c", "envsubst '$PORT' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
