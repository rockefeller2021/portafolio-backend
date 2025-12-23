# Portfolio Backend - Spring Boot 3.4.1

## 🚨 ACCIÓN REQUERIDA: Iniciar Docker Desktop

**Problema detectado:** Docker Desktop no está corriendo.

**Solución:**

1. Abre **Docker Desktop** desde el menú de inicio de Windows
2. Espera a que Docker Desktop inicie completamente (el ícono en la bandeja del sistema debe estar verde)
3. Una vez iniciado, ejecuta:
   ```bash
   cd C:\Users\magom\OneDrive\Desktop\Angular\portfolio-rafael\portfolio-backend
   docker-compose up -d
   ```

---

## 📋 Configuración del Proyecto

### Stack Tecnológico

- **Spring Boot:** 3.4.1
- **Java:** 21
- **Base de Datos:** MySQL 8.0 (Dockerizado)
- **Seguridad:** Spring Security + JWT
- **ORM:** Spring Data JPA
- **Validación:** Spring Validation
- **Utilidades:** Lombok

### Dependencias Principales

- `spring-boot-starter-web` - REST API
- `spring-boot-starter-data-jpa` - JPA/Hibernate
- `spring-boot-starter-security` - Seguridad
- `spring-boot-starter-validation` - Validaciones
- `mysql-connector-j` - Driver MySQL
- `lombok` - Reducir boilerplate
- `jjwt` (0.12.3) - JWT tokens

---

## 🐳 Docker MySQL

### Configuración

- **Imagen:** mysql:8.0
- **Puerto:** 3306
- **Base de datos:** portfolio_db
- **Usuario root:** root
- **Password:** Isaac2013\*
- **Usuario adicional:** portfolio_user
- **Password usuario:** Isaac2013\*

### Comandos Docker

**Iniciar MySQL:**

```bash
docker-compose up -d
```

**Ver logs:**

```bash
docker-compose logs -f mysql
```

**Detener MySQL:**

```bash
docker-compose down
```

**Detener y eliminar volúmenes:**

```bash
docker-compose down -v
```

---

## ⚙️ Configuración (application.properties)

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/portfolio_db
spring.datasource.username=root
spring.datasource.password=Isaac2013*

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=RafaelAlvaradoPortfolioSecretKey2024MinimumLengthRequired256Bits
jwt.expiration=86400000

# CORS
cors.allowed-origins=http://localhost:4200
```

---

## 🏗️ Estructura del Proyecto

```
com.rafaelalvarado.portfolio/
├── config/          # Configuración (Security, JWT, CORS)
├── controller/      # Controladores REST
├── dto/             # Data Transfer Objects
├── entity/          # Entidades JPA
├── repository/      # Repositorios
├── service/         # Lógica de negocio
└── exception/       # Manejo de excepciones
```

---

## 🚀 Ejecutar el Proyecto

### Prerequisitos

1. ✅ Java 21 instalado
2. ✅ Docker Desktop corriendo
3. ✅ MySQL dockerizado iniciado

### Pasos

**1. Iniciar MySQL (Docker):**

```bash
docker-compose up -d
```

**2. Compilar el proyecto:**

```bash
./mvnw clean install
```

**3. Ejecutar la aplicación:**

```bash
./mvnw spring-boot:run
```

**4. Verificar:**

- Backend: http://localhost:8080/api
- MySQL: localhost:3306

---

## 📝 Próximos Pasos

Una vez Docker esté corriendo:

1. ✅ Crear entidades (User, BlogPost, ContactMessage)
2. ✅ Crear repositorios JPA
3. ✅ Implementar servicios
4. ✅ Crear controladores REST
5. ✅ Configurar Spring Security + JWT
6. ✅ Probar endpoints

---

## 🔧 Troubleshooting

### Docker no inicia

- Verifica que Docker Desktop esté instalado
- Reinicia Docker Desktop
- Verifica que Hyper-V esté habilitado (Windows)

### Puerto 3306 ocupado

- Detén MySQL local si está corriendo
- Cambia el puerto en docker-compose.yml

### Error de conexión a MySQL

- Verifica que el contenedor esté corriendo: `docker ps`
- Revisa los logs: `docker-compose logs mysql`
- Espera a que MySQL termine de iniciar (healthcheck)

---

**Autor:** Rafael Alvarado García  
**Versión:** 0.0.1-SNAPSHOT
