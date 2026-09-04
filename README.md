Un sistema de gestión de tareas colaborativo desarrollado con Java Spring Boot y Thymeleaf, que permite a equipos de trabajo organizar proyectos, asignar responsabilidades y dar seguimiento en tiempo real al progreso de los usuarios.

👉 Demo desplegada en Render: https://gestor-tareas-wsj4.onrender.com/
 (actualmente no disponible por suscripción)
# 🖼️ Capturas de pantalla

🔑 Pantalla de Login con animaciones.
<img width="1907" height="885" alt="image" src="https://github.com/user-attachments/assets/7438a2d6-210a-4285-b0be-21704d21f381" />

🏠 Principal
<img width="1888" height="884" alt="image" src="https://github.com/user-attachments/assets/c7a639d8-8ecc-46bd-9b6e-8fb9dddf6a58" />

<img width="1894" height="883" alt="image" src="https://github.com/user-attachments/assets/1c0aa277-1e34-4cd8-8eda-7a6eb535a4c5" />

<img width="1909" height="878" alt="image" src="https://github.com/user-attachments/assets/8a1dced8-ec51-4d33-9db2-d7ee3ca254b8" />

<img width="1896" height="894" alt="image" src="https://github.com/user-attachments/assets/4a57a76e-750c-4f56-85f4-571dd5e36ef7" />

<img width="1905" height="855" alt="image" src="https://github.com/user-attachments/assets/37d939e3-6ada-4bdf-bda7-88077cc72ab3" />


<img width="1912" height="883" alt="image" src="https://github.com/user-attachments/assets/da3fdbab-c0c9-4537-a9dc-e17418f3aa90" />
📂 Gestión de proyectos
<img width="1906" height="878" alt="image" src="https://github.com/user-attachments/assets/d717f0bd-9214-472b-ab0f-a7f8af9bab7b" />

<img width="1899" height="879" alt="image" src="https://github.com/user-attachments/assets/17034f8c-978b-4c68-92c5-9d4a776f7e23" />

📂 Tablero Kanban
<img width="1910" height="884" alt="image" src="https://github.com/user-attachments/assets/08d5ff87-fb83-4892-93df-0ae4178866a7" />

✅ Creación de tareas
<img width="1910" height="889" alt="image" src="https://github.com/user-attachments/assets/30535a2a-9c7e-4123-831d-7097a661aeb7" />



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

⚡ Instalación y ejecución local
Requisitos previos

Java 17+

Maven 3+

PostgreSQL

Pasos
 1. Clonar el repositorio
git clone https://github.com/PaniaguaSanty/gestor-tareas.git

 2. Acceder al directorio
cd gestor-tareas

 5. Ejecutar
mvn spring-boot:run



🤝 Contribuciones

¡Las contribuciones son bienvenidas! 🎉
Puedes abrir un issue o enviar un pull request si deseas mejorar el proyecto.

📜 Licencia

Este proyecto se distribuye bajo la licencia MIT.
Consulta el archivo LICENSE para más detalles.

👤 Autor

Santiago Paniagua
LinkedIn: https://www.linkedin.com/in/santiago-paniagua-9066a6252/
