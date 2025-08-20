💬 API REST del Foro (Challenge Alura)

Este proyecto implementa una API REST para un foro, desarrollada como parte del Challenge de Alura ONE. Permite a los usuarios registrarse, autenticarse, crear tópicos de discusión, listar tópicos, detallar un tópico por ID, actualizar tópicos y eliminar tópicos. Incorpora seguridad basada en JWT (JSON Web Tokens) para proteger los endpoints.



🌟 Características

Autenticación de Usuarios: Registro básico y autenticación mediante email y contraseña, generando un JWT.



Gestión de Tópicos:



Creación de nuevos tópicos (requiere autenticación).



Listado de todos los tópicos (acceso público).



Detalle de un tópico por ID (requiere autenticación).



Actualización de tópicos por ID (requiere autenticación).



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



1\. Configuración de la Base de Datos

Crea la base de datos:



Abre PgAdmin 4 y conéctate a tu servidor PostgreSQL.



Crea una nueva base de datos llamada foro\_db. Asegúrate de que el propietario sea postgres (o tu usuario principal).



Configura las credenciales en application.properties:



Abre src/main/resources/application.properties.



Actualiza spring.datasource.username y spring.datasource.password con tus credenciales de PostgreSQL. El usuario más común es postgres.



spring.datasource.url=jdbc:postgresql://localhost:5432/foro\_db

spring.datasource.username=YOUR\_DB\_USERNAME\_HERE # Tu usuario de PostgreSQL

spring.datasource.password=YOUR\_DB\_PASSWORD\_HERE # Tu contraseña real de PostgreSQL



Configura la clave secreta JWT:



En el mismo application.properties, define una clave secreta fuerte y única para JWT.



api.security.secret=YOUR\_JWT\_SECRET\_HERE # Una clave secreta muy larga y segura para JWT



2\. Configuración del Proyecto en IntelliJ IDEA

Clona o descarga el proyecto y ábrelo en IntelliJ IDEA.



Recarga las dependencias de Maven: Asegúrate de que IntelliJ descargue todas las dependencias definidas en pom.xml. Busca el icono de Maven para "Reload All Maven Projects".



Limpia y reconstruye el proyecto:



Build -> Clean Project



Build -> Rebuild Project



3\. Insertar un Usuario Inicial en la Base de Datos

Para poder autenticarte y probar la API, necesitas un usuario en la base de datos.



En PgAdmin 4, navega a Databases -> foro\_db -> Schemas -> public -> Tables.



Haz clic derecho en la tabla users y selecciona "View/Edit Data" -> "All Rows".



Añade una nueva fila:



id: Déjalo en \[default] (se generará automáticamente).



email: test@example.com (o cualquier email para tu usuario de prueba).



password: Pega el hash BCrypt de tu contraseña (ej., si tu contraseña es 123456, usa el hash generado por BCryptPasswordEncoder).



role: USER (¡en mayúsculas!).



Haz clic en el icono del disquete (Save Data Changes) para guardar la fila.



4\. Ejecutar la Aplicación

En IntelliJ IDEA, abre la clase ForumApplication.java.



Haz clic derecho en el método main y selecciona "Run 'ForumApplication.main()'".



La aplicación se iniciará en el puerto 8080 por defecto.



🗺️ Endpoints de la API

Utiliza Postman (o Insomnia) para probar los siguientes endpoints:



1\. Autenticación (Obtener JWT)

URL: POST http://localhost:8080/auth



Headers:



Content-Type: application/json



Body (raw / JSON):



{

&nbsp;   "email": "test@example.com",

&nbsp;   "password": "123456"

}



Respuesta Exitosa: 200 OK con un JSON que contiene el jwtToken. Copia este token.



2\. Listar Tópicos (Público)

URL: GET http://localhost:8080/topics



Headers: (Ninguno específico)



Respuesta Exitosa: 200 OK con una lista JSON de tópicos.



3\. Crear Tópico (Protegido - Requiere Autenticación)

URL: POST http://localhost:8080/topics



Headers:



Content-Type: application/json



Authorization: Bearer TU\_TOKEN\_JWT\_AQUI (Reemplaza con el token obtenido en el paso 1 de Autenticación).



Body (raw / JSON):



{

&nbsp;   "title": "Duda sobre Spring Security",

&nbsp;   "message": "¿Cómo se gestionan los roles con JWT?",

&nbsp;   "courseName": "Desarrollo Backend",

&nbsp;   "userId": 1 // ID del usuario que creaste en la DB

}



Respuesta Exitosa: 201 Created con el JSON del tópico creado.



4\. Detallar Tópico por ID (Protegido - Requiere Autenticación)

URL: GET http://localhost:8080/topics/{id} (ej. http://localhost:8080/topics/1)



Headers:



Authorization: Bearer TU\_TOKEN\_JWT\_AQUI



Respuesta Exitosa: 200 OK con el JSON detallado del tópico.



5\. Actualizar Tópico (Protegido - Requiere Autenticación)

URL: PUT http://localhost:8080/topics/{id} (ej. http://localhost:8080/topics/2)



Headers:



Content-Type: application/json



Authorization: Bearer TU\_TOKEN\_JWT\_AQUI



Body (raw / JSON):



{

&nbsp;   "title": "Nuevo Título Actualizado",

&nbsp;   "message": "Este es el mensaje actualizado del tópico. ¡Funciona!",

&nbsp;   "courseName": "Actualizaciones de API"

}



Respuesta Exitosa: 200 OK con el JSON del tópico actualizado.



6\. Eliminar Tópico (Protegido - Requiere Autenticación)

URL: DELETE http://localhost:8080/topics/{id} (ej. http://localhost:8080/topics/4)



Headers:



Authorization: Bearer TU\_TOKEN\_JWT\_AQUI



Respuesta Exitosa: 204 No Content



💡 Posibles Mejoras

Implementar funcionalidad para cerrar tópicos (status: "CLOSED").



Añadir manejo de respuestas para tópicos no encontrados (personalizar 404).



Expandir la gestión de usuarios (registro de nuevos usuarios desde la API).



Añadir más modelos (ej. Respuestas a tópicos).



🤝 Contribuciones

¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar este foro, no dudes en abrir un "issue" o enviar un "pull request".



📄 Licencia

Este proyecto está bajo la Licencia MIT.

