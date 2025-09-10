# 📌 Gestor de Tareas

Un sistema de gestión de tareas colaborativo desarrollado con Java Spring Boot y Thymeleaf, que permite a equipos de trabajo organizar proyectos, asignar responsabilidades y dar seguimiento en tiempo real al progreso de los usuarios.

👉 Demo desplegada en Render
 (actualmente no disponible por suscripción)

# 🚀 Funcionalidades principales

👥 Gestión de usuarios: creación de usuarios independientes, sin necesidad de estar asociados a un equipo.

🏢 Equipos (Teams): organiza usuarios dentro de equipos de trabajo.

📂 Proyectos (Projects): cada equipo puede tener múltiples proyectos activos.

✅ Tareas (Tasks): creación, asignación opcional a un usuario, y seguimiento en tiempo real.

📎 Archivos adjuntos (Attachments): cada tarea puede tener documentos o archivos asociados.

💬 Comentarios (Comments): interacción entre usuarios dentro de cada tarea.

📊 Dashboard de seguimiento: visualización clara del estado de las tareas y el progreso de cada miembro.

# 🔄 Flujo de creación de entidades

Users → Se crean primero, pueden existir sin equipo.

Teams → Se crean equipos y se asignan usuarios.

Projects → Cada proyecto pertenece a un team.

Tasks → Se crean dentro de un proyecto, asignables opcionalmente a un user.

Attachments & Comments → Siempre asociados a una tarea.

🔗 Ejemplo:
User → Team → Project → Task → [Attachment | Comment]

# 🛠️ Tecnologías utilizadas

Backend: Java 17 · Spring Boot · Spring Data JPA

Frontend: Thymeleaf · HTML · CSS · JavaScript

Base de datos: MySQL

Control de dependencias: Maven

Arquitectura: MVC

# Despliegue: Render (Docker-ready)

# ⚡ Instalación y ejecución local
Requisitos previos

Java 17+

Maven 3+

PostgreSQL

Pasos
# 1. Clonar el repositorio
git clone https://github.com/PaniaguaSanty/gestor-tareas.git

# 2. Acceder al directorio
cd gestor-tareas

# 3. Configurar credenciales de base de datos en application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestor_tareas
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# 4. Construir el proyecto
mvn clean install

# 5. Ejecutar
mvn spring-boot:run


La aplicación estará disponible en:
👉 http://localhost:8080/

🖼️ Capturas de pantalla

(Agrega aquí las imágenes de tu aplicación para que quede más atractivo visualmente)

📂 Estructura del proyecto
gestor-tareas/
 ┣ src/
 ┃ ┣ main/
 ┃ ┃ ┣ java/com/gestor_tareas/   # Código fuente (MVC)
 ┃ ┃ ┣ resources/
 ┃ ┃ ┃ ┣ templates/              # Vistas Thymeleaf
 ┃ ┃ ┃ ┣ static/                 # CSS y JS
 ┃ ┃ ┃ ┗ application.yml  # Configuración
 ┃ ┗ test/                       # Tests unitarios
 ┣ pom.xml                       # Dependencias Maven
 ┣ Dockerfile
 ┗ compose.yaml
 ┗ render.yaml

🤝 Contribuciones

¡Las contribuciones son bienvenidas! 🎉
Puedes abrir un issue o enviar un pull request si deseas mejorar el proyecto.

📜 Licencia

Este proyecto se distribuye bajo la licencia MIT.
Consulta el archivo LICENSE para más detalles.

👤 Autor

Santiago Paniagua
🔗 GitHub
 · LinkedIn
