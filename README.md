# MiniBlog API

API REST desarrollada con Spring Boot para administrar publicaciones de un blog. Incluye autenticación con JWT, paginación de datos y documentación interactiva.

## Tecnologías usadas

* Java 17+
* Spring Boot
* Spring Security & JWT
* Spring Data JPA & H2 Database
* Swagger UI (Springdoc)
* Maven

## Cómo ejecutarlo

1. Clonar el repositorio y entrar a la carpeta:
   ```bash
   git clone [https://github.com/TU_USUARIO/miniblog-api.git](https://github.com/TU_USUARIO/miniblog-api.git)
   cd miniblog-api
2. Iniciar la aplicación
   ```bash
   ./mvnw spring-boot:run

La Api correrá en:

``http://localhost:8080``

## Endpoints
### Autenticación
- POST /api/auth/register - Registro de usuario
- POST /api/auth/login - Inicio de sesión (devuelve el token JWT)
### Publicaciones
- GET /api/posts - Listar publicaciones paginadas
- GET /api/posts/{id} - Obtener una publicación por ID
- POST /api/posts - Crear publicación (Requiere token)
- PUT /api/posts/{id} - Editar publicación (Requiere token)
- DELETE /api/posts/{id} - Eliminar publicación (Requiere token)
## Swagger UI y Base de Datos
### Swagger UI

``http://localhost:8080/swagger-ui/index.html``

Para probar endpoints protegidos:

1. Haz login.
2. Copia el token JWT.
3. Pégalo en el botón "Authorize".
### Consola H2

``http://localhost:8080/h2-console``

### JDBC URL:

``jdbc:h2:mem:miniblogdb``

### Usuario:

``SA``
