# MediCare - Evaluacion Final (Backend REST + Frontend)

Sistema para la clinica MediCare: gestiona medicos, pacientes y consultas,
con seguridad por roles y una API REST consumida desde un frontend aparte.

El repositorio contiene dos proyectos en carpetas separadas:
- `backend/`  -> API REST con Spring Boot, Spring Security y JPA.
- `frontend/` -> pagina HTML/CSS/JS que consume la API.

## Backend

### Requisitos
- JDK 21.
- MySQL en ejecucion.

### Ejecutar
1. La base se crea sola
2. En `backend/src/main/resources/application.properties` ajustar usuario y contraseña.
3. Ejecuta `MedicareApplication`. Corre en `http://localhost:8080`.

### Seguridad
- Autenticacion HTTP Basic; contrasenas cifradas con BCrypt.
- GET: roles ADMIN y USER. POST, PUT y DELETE: solo ADMIN.

### Relaciones
- Medico 1:1 Perfil.
- Medico 1:N Consulta.
- Paciente N:M Medico, concretada por la entidad Consulta.

### Endpoints (todos bajo /api, con CORS)
- GET  /api/me
- GET  /api/medicos        | GET /api/medicos/{id}
- POST /api/medicos        | PUT /api/medicos/{id} | DELETE /api/medicos/{id}
- GET  /api/medicos/especialidad/{especialidad}     (consulta personalizada a)
- GET  /api/pacientes      | GET /api/pacientes/{id}
- POST /api/pacientes      | PUT /api/pacientes/{id} | DELETE /api/pacientes/{id}
- GET  /api/pacientes/buscar?nombre=texto           (consulta personalizada b)
- GET  /api/consultas      | POST /api/consultas?medicoId=1&pacienteId=1

## Frontend
Abre `frontend/index.html` en el navegador (o sirvelo con Live Server).
Permite iniciar sesion, listar medicos y pacientes, y crear un registro de cada uno.
El backend debe estar corriendo en el puerto 8080.

## Entrega GitHub
Enlace: https://github.com/Jhosu25/Evaluacion_LP3.git
