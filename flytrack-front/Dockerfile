# ---- Etapa 1: Build Angular ----
FROM node:20-alpine AS builder

WORKDIR /app

# Copiar manifiestos primero para aprovechar cache de Docker
COPY package*.json ./
# npm install en lugar de npm ci porque no hay package-lock.json generado aun
RUN npm install --silent

# Copiar el resto del codigo y compilar para produccion
COPY . .
RUN npm run build:prod

# ---- Etapa 2: Servir con Nginx ----
FROM nginx:1.25-alpine AS runtime

# Eliminar configuracion por defecto de Nginx
RUN rm /etc/nginx/conf.d/default.conf

# Copiar configuracion personalizada
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copiar el build de Angular
COPY --from=builder /app/dist/flytrack-front/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
