# MiniBlog API

RESTful API desarrollada con Spring Boot para la gestión de publicaciones en una plataforma de blog. Incorpora seguridad basada en JWT (JSON Web Tokens), paginación dinámica, almacenamiento en memoria con H2 Database y documentación interactiva mediante OpenAPI / Swagger UI.

## Características

* **Autenticación y Autorización**: Sistema de registro e inicio de sesión seguro usando Spring Security y JJWT.
* **Gestión de Publicaciones (CRUD)**: Creación, lectura, actualización y eliminación de publicaciones.
* **Paginación y Ordenamiento**: Control dinámico de resultados mediante `Pageable` (`page`, `size`, `sortBy`, `sortDir`).
* **Documentación Interactiva**: Integración con Swagger UI configurada para admitir autenticación `Bearer` en pruebas en vivo.

## Tecnologías Utilizadas

| Tecnología | Descripción |
| --- | --- |
| **Java** | Lenguaje principal del proyecto |
| **Spring Boot** | Framework base para el desarrollo de la API |
| **Spring Security** | Manejo de autenticación, autorización y filtros de seguridad |
| **Spring Data JPA** | Persistencia de datos y mapeo objeto-relacional (ORM) |
| **H2 Database** | Base de datos relacional en memoria para entorno de desarrollo |
| **JJWT (io.jsonwebtoken)** | Creación y validación de tokens JWT |
| **Springdoc OpenAPI** | Generación automática de documentación Swagger UI (v2.8.4) |
| **Lombok** | Reducción de código repetitivo (Boilerplate) |
| **Maven** | Gestor de dependencias y construcción |

## Requisitos Previos

* **JDK**: Versión 17 o superior instalada y configurada en el sistema.
* **Maven**: Gestor de proyectos de Java (o utilizar el wrapper incluido `./mvnw`).

## Instalación y Ejecución

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/TU_USUARIO/miniblog-api.git](https://github.com/TU_USUARIO/miniblog-api.git)
   cd miniblog-api
