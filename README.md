# 💬 API REST del Foro (Challenge Alura)

Este proyecto implementa una  **API REST para un foro** , desarrollada como parte del  *Challenge de Alura ONE* .

Permite a los usuarios registrarse, autenticarse, crear y gestionar tópicos de discusión.

La seguridad está implementada con **JWT (JSON Web Tokens)** para proteger los endpoints.

---

## 🌟 Características

* 🔑  **Autenticación de Usuarios** : Registro y login con email/contraseña → genera JWT.
* 📝  **Gestión de Tópicos** :
  * Crear nuevos tópicos (requiere autenticación).
  * Listar todos los tópicos (público).
  * Ver detalle de un tópico por ID (requiere autenticación).
  * Actualizar y eliminar tópicos (requiere autenticación).
* 🛡️  **Seguridad** : Spring Security con JWT.
* 🗄️  **Base de Datos** : PostgreSQL con Spring Data JPA.
* ✅ **Validación de Datos** con `jakarta.validation`.

---

## 🛠️ Tecnologías Utilizadas

* ☕ Java 17
* 🚀 Spring Boot 3.x (Web, Data JPA, Security, Validation, DevTools)
* 🗄️ PostgreSQL
* 📦 Maven
* 📝 Lombok
* 🔐 Auth0 java-jwt

---

## 🚀 Cómo Empezar

### 📌 Requisitos Previos

* JDK 17+
* Maven
* PostgreSQL (5432 por defecto)
* PgAdmin 4 (opcional)
* IDE (IntelliJ IDEA recomendado)
* Postman / Insomnia

---

### 1️⃣ Configuración de la Base de Datos

<pre class="overflow-visible!" data-start="1598" data-end="1792"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><span class="" data-state="closed"></span></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-properties"><span>spring.datasource.url=jdbc:postgresql://localhost:5432/foro_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

api.security.secret=YOUR_JWT_SECRET
</span></code></div></div></pre>

### 2️⃣ Insertar un Usuario Inicial

En la tabla `users` de la DB:

<pre class="overflow-visible!" data-start="1860" data-end="1944"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><span class="" data-state="closed"></span></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-sql"><span><span>email: test</span><span>@example</span><span>.com
password: </span><span><</span><span>hash_bcrypt_de_tu_password</span><span>></span><span>
role: </span><span>USER</span><span>
</span></span></code></div></div></pre>

### 3️⃣ Ejecutar la Aplicación

En IntelliJ → `ForumApplication.java` →  **Run** .

Se levanta en `http://localhost:8080`.

---

## 🗺️ Endpoints de la API

### 🔐 1. Autenticación

**POST** `http://localhost:8080/auth`

Body:

<pre class="overflow-visible!" data-start="2171" data-end="2240"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><span class="" data-state="closed"></span></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-json"><span><span>{</span><span>
  </span><span>"email"</span><span>:</span><span></span><span>"test@example.com"</span><span>,</span><span>
  </span><span>"password"</span><span>:</span><span></span><span>"123456"</span><span>
</span><span>}</span><span>
</span></span></code></div></div></pre>

✅ Devuelve `jwtToken`.

📸 *Postman mostrando login y token devuelto*

![Login exitoso en Postman](assets/Autorización-del-tópico.png)

---

### 🌍 2. Listar Tópicos

**GET** `http://localhost:8080/topics`

✅ Devuelve lista JSON.

📸 *Respuesta con lista de tópicos en Postman*

![Lista de tópicos en Postman](assets/Mostrar-todos-los-tópicos.png)

---

### ➕ 3. Crear Tópico

**POST** `http://localhost:8080/topics`

Headers: `Authorization: Bearer <JWT>`

Body:

<pre class="overflow-visible!" data-start="2613" data-end="2775"><div class="contain-inline-size rounded-2xl relative bg-token-sidebar-surface-primary"><div class="sticky top-9"><div class="absolute end-0 bottom-0 flex h-9 items-center pe-2"><div class="bg-token-bg-elevated-secondary text-token-text-secondary flex items-center gap-4 rounded-sm px-2 font-sans text-xs"><span class="" data-state="closed"></span></div></div></div><div class="overflow-y-auto p-4" dir="ltr"><code class="whitespace-pre! language-json"><span><span>{</span><span>
  </span><span>"title"</span><span>:</span><span></span><span>"Duda sobre Spring Security"</span><span>,</span><span>
  </span><span>"message"</span><span>:</span><span></span><span>"¿Cómo se gestionan los roles con JWT?"</span><span>,</span><span>
  </span><span>"courseName"</span><span>:</span><span></span><span>"Desarrollo Backend"</span><span>,</span><span>
  </span><span>"userId"</span><span>:</span><span></span><span>1</span><span>
</span><span>}</span><span>
</span></span></code></div></div></pre>

📸 *Postman mostrando creación de tópico*

![Creación y detalle de tópico en Postman](assets/Registro-de-un-nuevo-tópico-y-detallado.png)

---

### 📌 4. Detalle de Tópico

**GET** `http://localhost:8080/topics/{id}`

Headers: `Authorization: Bearer <JWT>`

📸 *Postman mostrando detalle de un tópico*

![Creación y detalle de tópico en Postman](assets/Registro-de-un-nuevo-tópico-y-detallado.png)


---

### ✏️ 5. Actualizar Tópico

**PUT** `http://localhost:8080/topics/{id}`

📸 *Postman mostrando actualización exitosa*

![Actualización de tópico en Postman](assets/Tópico-actualizado.png)

![Vista de tópicos actualizados en Postman](assets/Tópicos-actualizados.png)

---

### 🗑️ 6. Eliminar Tópico

**DELETE** `http://localhost:8080/topics/{id}`

📸 *Postman mostrando respuesta 204 No Content*

![Eliminación de tópico en Postman](assets/Eliminación-del-tópico.png)

---

## 💡 Posibles Mejoras

* Estado de tópicos (`OPEN` / `CLOSED`).
* Respuestas personalizadas para errores (ej. 404).
* Registro de usuarios desde la API.
* Añadir modelo de **Respuestas** a los tópicos.

---

## 🤝 Contribuciones

¡Bienvenidas las ideas y mejoras! 🎉

Abre un **issue** o un  **pull request** .

---

## 📄 Licencia

Proyecto bajo  **Licencia MIT** .
