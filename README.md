💬 API REST del Foro (Challenge Alura)
Este proyecto implementa una API REST para un foro, desarrollada como parte del Challenge de Alura ONE. Permite a los usuarios registrarse, autenticarse, crear tópicos de discusión, listar tópicos y eliminar tópicos. Incorpora seguridad basada en JWT (JSON Web Tokens) para proteger los endpoints.

🌟 Características
Autenticación de Usuarios: Registro básico y autenticación mediante email y contraseña, generando un JWT.

Gestión de Tópicos:

Creación de nuevos tópicos (requiere autenticación).

Listado de todos los tópicos (acceso público).

Eliminación de tópicos por ID (requiere autenticación).

Seguridad: Implementación de Spring Security con JWT para proteger los recursos de la API.

Base de Datos: Persistencia de datos con PostgreSQL y Spring Data JPA.

Validación de Datos: Uso de jakarta.validation para validar los DTOs de entrada.

🛠️ Tecnologías Utilizadas
Java 17: Lenguaje de programación.

Spring Boot 3.x: Framework para el desarrollo rápido de APIs REST.

spring-boot-starter-web: Para construir APIs web.

spring-boot-starter-data-jpa: Para interactuar con la base de datos de forma sencilla.

spring-boot-starter-security: Para la seguridad de la aplicación.

spring-boot-starter-validation: Para validar los datos de entrada.

spring-boot-devtools: Herramientas de desarrollo que facilitan los reinicios.

PostgreSQL: Sistema de gestión de bases de datos relacionales.

Maven: Herramienta de gestión de dependencias y construcción del proyecto.

Lombok: Librería para reducir el código repetitivo (boilerplate) mediante anotaciones (ej. @Getter, @NoArgsConstructor).

java-jwt (Auth0): Librería para la implementación de JSON Web Tokens.

🚀 Cómo Empezar
Sigue estos pasos para levantar y probar la API en tu entorno local.

Requisitos Previos
Asegúrate de tener instalado lo siguiente:

JDK 17 o superior.

Maven (generalmente incluido con los IDEs modernos como IntelliJ IDEA).

PostgreSQL instalado y ejecutándose localmente (puerto por defecto 5432).

PgAdmin 4 (o cualquier cliente PostgreSQL) para gestionar la base de datos.

IntelliJ IDEA (o tu IDE de preferencia).

Postman (o Insomnia) para probar los endpoints de la API.

1. Configuración de la Base de Datos
Crea la base de datos:

Abre PgAdmin 4 y conéctate a tu servidor PostgreSQL.

Crea una nueva base de datos llamada foro_db. Asegúrate de que el propietario sea postgres (o tu usuario principal).

Configura las credenciales en application.properties:

Abre src/main/resources/application.properties.

Actualiza spring.datasource.username y spring.datasource.password con tus credenciales de PostgreSQL. El usuario más común es postgres.

spring.datasource.url=jdbc:postgresql://localhost:5432/foro_db
spring.datasource.username=postgres # Tu usuario de PostgreSQL
spring.datasource.password=tu_contraseña_real_aqui # Tu contraseña real de PostgreSQL

Configura la clave secreta JWT:

En el mismo application.properties, define una clave secreta fuerte y única para JWT.

api.security.secret=una_clave_secreta_muy_larga_y_segura_para_jwt

Puedes generar una en Java con:

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "123456"; // Contraseña a hashear
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Hash BCrypt para '123456': " + encodedPassword);
    }
}

(Este código genera un hash para 123456, pero la clave api.security.secret debe ser una cadena aleatoria y larga, no un hash de contraseña).

2. Configuración del Proyecto en IntelliJ IDEA
Clona o descarga el proyecto y ábrelo en IntelliJ IDEA.

Recarga las dependencias de Maven: Asegúrate de que IntelliJ descargue todas las dependencias definidas en pom.xml. Busca el icono de Maven para "Reload All Maven Projects".

Limpia y reconstruye el proyecto:

Build -> Clean Project

Build -> Rebuild Project

3. Insertar un Usuario Inicial en la Base de Datos
Para poder autenticarte y probar la API, necesitas un usuario en la base de datos.

En PgAdmin 4, navega a Databases -> foro_db -> Schemas -> public -> Tables.

Haz clic derecho en la tabla users y selecciona "View/Edit Data" -> "All Rows".

Añade una nueva fila:

id: Déjalo en [default] (se generará automáticamente).

email: test@example.com (o cualquier email para tu usuario de prueba).

password: Pega el hash BCrypt de tu contraseña (ej., si tu contraseña es 123456, usa el hash generado por BCryptPasswordEncoder como: $2a$10$y58o.iE.PqD4BqN8N8yQ.u5V.e.a.r.c.g.o.e.l.c.a.p.i.T.a.L.2023).

role: USER (¡en mayúsculas!).

Haz clic en el icono del disquete (Save Data Changes) para guardar la fila.

4. Ejecutar la Aplicación
En IntelliJ IDEA, abre la clase ForumApplication.java.

Haz clic derecho en el método main y selecciona "Run 'ForumApplication.main()'".

La aplicación se iniciará en el puerto 8080 por defecto.

🗺️ Endpoints de la API
Utiliza Postman (o Insomnia) para probar los siguientes endpoints:

1. Autenticación (Obtener JWT)
URL: POST http://localhost:8080/auth

Headers:

Content-Type: application/json

Body (raw / JSON):

{
    "email": "test@example.com",
    "password": "123456"
}

Respuesta Exitosa: 200 OK con un JSON que contiene el jwtToken. Copia este token.

2. Listar Tópicos (Público)
URL: GET http://localhost:8080/topics

Headers: (Ninguno específico)

Respuesta Exitosa: 200 OK con una lista JSON de tópicos.

3. Crear Tópico (Protegido - Requiere Autenticación)
URL: POST http://localhost:8080/topics

Headers:

Content-Type: application/json

Authorization: Bearer TU_TOKEN_JWT_AQUI (Reemplaza con el token obtenido en el paso 1).

Body (raw / JSON):

{
    "title": "Duda sobre Spring Security",
    "message": "¿Cómo se gestionan los roles con JWT?",
    "courseName": "Desarrollo Backend",
    "userId": 1 // ID del usuario que creaste en la DB
}

Respuesta Exitosa: 201 Created con el JSON del tópico creado.

4. Eliminar Tópico (Protegido - Requiere Autenticación)
URL: DELETE http://localhost:8080/topics/{id} (ej. http://localhost:8080/topics/1)

Headers:

Authorization: Bearer TU_TOKEN_JWT_AQUI

Respuesta Exitosa: 204 No Content

💡 Posibles Mejoras
Implementar funcionalidad para actualizar tópicos.

Añadir manejo de respuestas para tópicos no encontrados (personalizar 404).

Expandir la gestión de usuarios (registro de nuevos usuarios desde la API).

Añadir validaciones más robustas y manejo de excepciones global.

Implementar paginación en el listado de tópicos.

Añadir más modelos (ej. Respuestas a tópicos).

🤝 Contribuciones
¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar este foro, no dudes en abrir un "issue" o enviar un "pull request".

📄 Licencia
Este proyecto está bajo la Licencia MIT.