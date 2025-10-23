# 🧩 Chakray User Management API

API REST desarrollada en **Spring Boot 3 (Java 17)** que permite realizar un **CRUD de usuarios** almacenados en un archivo JSON.  
Incluye autenticación básica (`/login`), cifrado AES256 de contraseñas y validaciones de formato.

---

## 🚀 Requerimientos del sistema

Antes de iniciar, asegúrate de tener instalado:

| Herramienta | Versión recomendada |
|--------------|---------------------|
| [Java JDK](https://adoptium.net/) | 17 o superior |
| [Maven](https://maven.apache.org/) | 3.9 o superior |
| [Git](https://git-scm.com/) | Última versión estable |

> 💡 Verifica las versiones instaladas:
> ```bash
> java -version
> mvn -version
> git --version
> ```

---

## 📥 Instalación y configuración

1️⃣ **Clona el repositorio**

```bash
git clone https://github.com/RobertDev-DDVC/test-chakray
cd test-chakray
```
## 📁 Estructura del proyecto

```
src/
├── main/
│   ├── java/com/test/chakray/
│   │   ├── controller/       → Controladores REST
│   │   ├── dto/              → Objetos de transferencia (DTOs)
│   │   ├── exception/        → Manejo global de errores
│   │   ├── model/            → Entidades (User, Address)
│   │   ├── security/         → Cifrado AES256
│   │   ├── service/          → Lógica de negocio
│   │   └── utils/            → Persistencia JSON
│   └── resources/
│       ├── application.properties
│       └── data/users.json (se crea automáticamente)
```
## 🚀 Urls disponibles
Iniciar sesión: <http://localhost:8081/login>.
    
Operaciones CRUD con usuarios: <http://localhost:8081/api/users>
