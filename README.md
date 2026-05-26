# # 🎫 Tickeria - Sistema de Gestión de Tickets 

Tickeria es una aplicación Full Stack diseñada para gestionar flujos de trabajo mediante un tablero Kanban dinámico. Permite la creación de tickets clasificados por prioridad y cliente, así como su movimiento interactivo entre diferentes estados físicos de progreso.

## 🛠️ Arquitectura y Tecnologías

El proyecto está dividido en un ecosistema de tres capas:

1. **Base de Datos (Almacenamiento)**: MySQL 8.0 corriendo en un contenedor aislado de **Docker**.
2. **Backend (Cerebro)**: **Java 21** con **Spring Boot 4.x** e Hibernate/Spring Data JPA para la persistencia segura.
3. **Frontend (Interfaz)**: **Vue 3** (Composition API) administrado con **Vite**, estilizado con **Tailwind CSS** y conectado mediante **Axios**.

---

## 🚀 Guía de Encendido Rápido

Para levantar todo el ecosistema localmente, ejecuta los siguientes comandos en orden:

### 1. Base de Datos (Docker)
Entra a la raíz del proyecto y arranca el contenedor en segundo plano:
```bash
docker compose up -d

2. Backend (Java Spring Boot)
Abre el proyecto en tu IDE (VS Code), navega hasta TickeriaBackendApplication.java y ejecuta el modo Run o Debug.
La API REST estará escuchando de forma segura en http://localhost:8080.

3. Frontend (Vue.js)
Abre una terminal nueva, muévete a la carpeta de la interfaz e inicia el servidor de desarrollo de Vite:
cd tickeria-frontend
npm run dev