# 🎫 Tickeria - Sistema de Gestión de Tickets (Tablero Kanban Full-Stack)

Tickeria es una aplicación Full Stack de nivel profesional diseñada para gestionar flujos de trabajo eficientemente mediante un tablero Kanban dinámico. Permite la creación, organización y arrastre interactivo de tickets clasificados por prioridad, cliente y estado físico de progreso.

Esta versión destaca porque **todo el ecosistema está completamente automatizado y contenedorizado con Docker**. Esto permite levantar el proyecto completo (Base de Datos, Backend y Frontend) con **un solo comando**, sin necesidad de instalar dependencias locales como Java, Node.js o MySQL de forma nativa en tu sistema operativo.

---

## 🛠️ Arquitectura y Tecnologías

El proyecto se organiza en una arquitectura aislada de tres capas orquestadas mediante **Docker Compose**:

| Capa | Tecnología Principal | Descripción | Puerto Interno | Puerto Externo (PC) |
| :--- | :--- | :--- | :---: | :---: |
| **Frontend** | Vue 3 (Composition API) + Vite + Tailwind CSS + Axios | Interfaz de usuario responsiva, animada y fluida para el tablero Kanban. | `5173` | `5173` |
| **Backend** | Java 21 + Spring Boot 3.x + Hibernate / Spring Data JPA | API REST robusta encargada de la lógica de negocio y mapeo relacional (ORM). | `8080` | `8080` |
| **Base de Datos** | MySQL 8.0 | Almacenamiento persistente de tickets mediante volúmenes aislados. | `3306` | `3307` |

---

## 📋 Prerrequisitos

Para ejecutar este proyecto de forma global solo necesitas tener instalado en tu máquina:
* **Git** (para clonar el repositorio)
* **Docker Desktop** (que incluye Docker Compose) en ejecución.

---
### 1. Clonar el repositorio
2. Levantar el ecosistema con Docker
Asegúrate de estar en la carpeta raíz del proyecto (donde se encuentra el archivo docker-compose.yml) y ejecuta:

docker compose up -d --build

Una vez que el proceso de Docker finalice con éxito, podrás acceder a los siguientes componentes desde tu computadora:

💻 Interfaz de Usuario (Frontend): Entra a http://localhost:5173 en tu navegador para interactuar con el tablero Kanban.

⚙️ API REST (Backend): Verifica que los endpoints respondan correctamente visitando http://localhost:8080/api/tickets.

🗄️ Base de Datos (MySQL): Si usas un gestor externo (como Navicat o DBeaver), conéctate usando:

Host: localhost

Puerto: 3307

Usuario: tickeria_user

Contraseña: tickeria_password

Base de datos: tickeria_db
