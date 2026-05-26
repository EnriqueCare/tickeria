# Etapa 1: Construcción de los archivos estáticos de Vue
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# Etapa 2: Servidor ligero para levantar el empaquetado de producción
FROM node:20-alpine
WORKDIR /app

# Instala un servidor estático rápido globalmente
RUN npm install -g serve

# Copiamos la carpeta 'dist' 
COPY --from=build /app/dist ./dist

# Exponer el puerto configurado en tu CORS de Spring Boot
EXPOSE 5173

# Arrancar el servidor en el puerto 5173
CMD ["serve", "-s", "dist", "-l", "5173"]