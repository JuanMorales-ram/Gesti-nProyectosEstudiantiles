#  Sistema de Gestión de Proyectos Estudiantiles
**Fundación Universitaria Compensar**

Sistema web para gestionar proyectos estudiantiles interdisciplinarios. Permite registrar estudiantes, docentes, proyectos, actividades y entregables a través de una API REST consumida desde un frontend en JavaScript puro.

---

## Tecnologías Utilizadas
------------------------
| Capa | Tecnología 
|-----------------------
| Lenguaje | Java 24 
| Servidor | Apache Tomcat 11.0.18 
| Persistencia | JPA con Hibernate 6.4.4 
| API REST | Jersey (JAX-RS) 3.1.5 
| Base de datos | MariaDB 10.4.32 (XAMPP) 
| Serialización | Jackson 2.17.0 
| Frontend | HTML5 + CSS3 + JavaScript puro (Fetch API) 
| Build | Maven 
| IDE | Apache NetBeans 24 
------------------------------


##  Estructura del Proyecto

GestionProyectosEstudiantiles/
│
├── Web Pages/                              # Archivos del frontend
│   ├── META-INF/                           # Metadatos del contexto web
│   ├── WEB-INF/                            # Configuración interna (web.xml, beans.xml)
│   ├── app.js                              # Lógica JavaScript - consume la API REST con fetch()
│   ├── index.html                          # Página principal del sistema
│   └── style.css                           # Estilos CSS de la interfaz
│
├── Source Packages/
│   └── com.unipanamericana.gestionproyectosestudiantiles/
│       │
│       ├── JakartaRestConfiguration.java   # Define el @ApplicationPath("/api") de JAX-RS
│       │
│       ├── config/
│       │   ├── AplicationConfig.java       # Registra los recursos REST de la aplicación
│       │   └── ObjectMapperProvider.java   # Configura Jackson para serializar fechas (LocalDate)
│       │
│       ├── model/                          # Entidades JPA mapeadas a tablas de la BD
│       │   ├── Actividad.java              # Tabla: actividades — tareas dentro de un proyecto
│       │   ├── Docente.java                # Tabla: docentes — docente que orienta el proyecto
│       │   ├── Entregable.java             # Tabla: entregables — archivos entregados por estudiantes
│       │   ├── Estudiante.java             # Tabla: estudiantes — estudiantes participantes
│       │   └── Proyecto.java              # Tabla: proyectos — proyectos interdisciplinarios
│       │
│       ├── resources/                      # Controladores REST (Capa Resource)
│       │   ├── ActividadResources.java     # Endpoints CRUD para /api/actividades
│       │   ├── DocenteResources.java       # Endpoints CRUD para /api/docentes
│       │   ├── EntregableResources.java    # Endpoints CRUD para /api/entregables
│       │   ├── EstudianteResources.java    # Endpoints CRUD para /api/estudiantes
│       │   └── ProyectoResources.java      # Endpoints CRUD para /api/proyectos
│       │
│       └── service/                        # Capa de lógica de negocio (Service)
│           ├── JPAUtil.java                # Crea y gestiona el EntityManagerFactory
│           ├── ActividadService.java       # Lógica: crear, listar, actualizar, eliminar actividades
│           ├── DocenteService.java         # Lógica: crear, listar, actualizar, eliminar docentes
│           ├── EntregableService.java      # Lógica: crear, listar, actualizar, eliminar entregables
│           ├── EstudianteService.java      # Lógica: crear, listar, actualizar, eliminar estudiantes
│           └── ProyectoService.java        # Lógica: crear, listar, actualizar, eliminar proyectos
│
├── Other Sources/
│   └── src/main/resources/META-INF/
│       └── persistence.xml                 # Configuración JPA: conexión a BD, Hibernate dialect
│
├── Dependencies/                           # Librerías Maven (Jersey, Hibernate, MySQL, Jackson)
├── Runtime Dependencies/
├── Java Dependencies/
│   └── JDK 21 (Default)
│
└── Project Files/
    ├── pom.xml                             # Dependencias y configuración del proyecto Maven
    └── nb-configuration.xml               # Configuración específica de NetBeans
---

## 🗄️ Base de Datos

### Credenciales

| Parámetro | Valor |
|-----------|-------|
| Host | localhost |
| Puerto | 3306 |
| Base de datos | gestion_proyectosestudiantiles |
| Usuario | root |
| Contraseña | *(vacía)* |

### Crear la base de datos

Abrir **phpMyAdmin** en `http://localhost/phpmyadmin` y ejecutar el siguiente SQL:

```sql
CREATE DATABASE gestion_proyectosestudiantiles
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE gestion_proyectosestudiantiles;

CREATE TABLE docentes (
    id_docente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    area VARCHAR(100) NOT NULL
);

CREATE TABLE estudiantes (
    id_estudiante INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    programa_academico VARCHAR(100) NOT NULL
);

CREATE TABLE proyectos (
    id_proyecto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    id_docente INT NOT NULL,
    FOREIGN KEY (id_docente) REFERENCES docentes(id_docente)
);

CREATE TABLE actividades (
    id_actividad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha_entrega DATE NOT NULL,
    id_proyecto INT NOT NULL,
    FOREIGN KEY (id_proyecto) REFERENCES proyectos(id_proyecto)
);

CREATE TABLE entregables (
    id_entregable INT AUTO_INCREMENT PRIMARY KEY,
    estado ENUM('pendiente','en progreso','entregado') NOT NULL DEFAULT 'pendiente',
    fecha_subida DATE,
    id_actividad INT NOT NULL,
    id_estudiante INT NOT NULL,
    FOREIGN KEY (id_actividad) REFERENCES actividades(id_actividad),
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(id_estudiante)
);
```

---

## Instrucciones para Ejecutar el Proyecto

### Requisitos previos

- Java JDK 24 instalado
- Apache NetBeans 24
- Apache Tomcat 11.0.18 configurado en NetBeans
- XAMPP con MariaDB corriendo en el puerto 3306

### Paso 1 — Iniciar la base de datos

1. Abrir **XAMPP Control Panel**
2. Hacer clic en **Start** al lado de **MySQL**
3. Verificar que el botón quede en verde (Running)

### Paso 2 — Abrir el proyecto en NetBeans

1. Abrir **Apache NetBeans 24**
2. Ir a **File → Open Project**
3. Navegar hasta la carpeta `GestionProyectosEstudiantiles` y abrirla
4. Esperar que Maven descargue las dependencias automáticamente

### Paso 3 — Compilar el proyecto

1. Clic derecho sobre el proyecto en el panel **Projects**
2. Seleccionar **Clean and Build**
3. Verificar que la consola muestre `BUILD SUCCESS`

### Paso 4 — Ejecutar el proyecto

1. Clic derecho sobre el proyecto
2. Seleccionar **Run**
3. Tomcat se iniciará automáticamente y abrirá el navegador

### Paso 5 — Acceder al sistema

- **Frontend:** `http://localhost:8081/GestionProyectosEstudiantiles/`
- **API REST:** `http://localhost:8081/GestionProyectosEstudiantiles/api/`

---

## 🔌 Endpoints de la API REST

Base URL: `http://localhost:8081/GestionProyectosEstudiantiles/api`

### Docentes `/docentes`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/docentes` | Listar todos los docentes |
| GET | `/docentes/{id}` | Buscar docente por ID |
| POST | `/docentes` | Crear nuevo docente |
| PUT | `/docentes/{id}` | Actualizar docente |
| DELETE | `/docentes/{id}` | Eliminar docente |

### Estudiantes `/estudiantes`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/estudiantes` | Listar todos los estudiantes |
| GET | `/estudiantes/{id}` | Buscar estudiante por ID |
| POST | `/estudiantes` | Crear nuevo estudiante |
| PUT | `/estudiantes/{id}` | Actualizar estudiante |
| DELETE | `/estudiantes/{id}` | Eliminar estudiante |

### Proyectos `/proyectos`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/proyectos` | Listar todos los proyectos |
| GET | `/proyectos/{id}` | Buscar proyecto por ID |
| POST | `/proyectos` | Crear nuevo proyecto |
| PUT | `/proyectos/{id}` | Actualizar proyecto |
| DELETE | `/proyectos/{id}` | Eliminar proyecto |

### Actividades `/actividades`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/actividades` | Listar todas las actividades |
| GET | `/actividades/{id}` | Buscar actividad por ID |
| POST | `/actividades` | Crear nueva actividad |
| PUT | `/actividades/{id}` | Actualizar actividad |
| DELETE | `/actividades/{id}` | Eliminar actividad |

### Entregables `/entregables`
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/entregables` | Listar todos los entregables |
| GET | `/entregables/{id}` | Buscar entregable por ID |
| POST | `/entregables` | Crear nuevo entregable |
| PUT | `/entregables/{id}` | Actualizar entregable |
| DELETE | `/entregables/{id}` | Eliminar entregable |


##  Configuración del Servidor Tomcat en NetBeans

| Parámetro | Valor |
|-----------|-------|
| Nombre | Apache Tomcat or TomEE |
| Ruta (Catalina Home) | `C:\tomcat11\apache-tomcat-11.0.18` |
| Puerto HTTP | 8081 |
| Puerto Shutdown | 8006 |
| Usuario manager | morales |
| Contraseña manager | tomcat11 |

---

## Notas Importantes

- Siempre iniciar MySQL en XAMPP antes de ejecutar el proyecto.
- Hibernate está configurado con `hbm2ddl.auto=update`, lo que actualiza las tablas automáticamente si hay cambios en las entidades.
- El frontend consume la API usando `fetch()` nativo de JavaScript, sin frameworks externos.
- Los estados válidos para entregables son: `pendiente`, `en_progreso`, `entregado`.
- URL principal del proyecto:  http://localhost:8081/GestionProyectosEstudiantiles/
