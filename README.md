# Task Manager API

A Spring Boot application for managing customer calls and notes with authentication and role-based access control.

## Features

- User Authentication with JWT
- Role-based Access Control (Admin/User)
- Call Management
  - Create, read, update, and delete call records
  - Track customer problems and solutions
- Note Management
  - Create, read, update, and delete business notes
  - Track customer contacts and situations
- Swagger Documentation
- PostgreSQL Database
- Docker Support

## Tech Stack

- [Kotlin](https://kotlinlang.org/) - Programming Language
- [Spring Boot](https://spring.io/projects/spring-boot) - Backend Framework
- [Spring Security](https://spring.io/projects/spring-security) - Authentication & Authorization
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Data Access
- [PostgreSQL](https://www.postgresql.org/) - Database
- [JWT](https://jwt.io/) - Token-based Authentication
- [Gradle](https://gradle.org/) - Build Tool
- [Docker](https://www.docker.com/) - Containerization

## API Endpoints

### Authentication

#### Login

```http
POST /auth/login
```

Request body:

```json
{
  "email": "string",
  "password": "string"
}
```

Returns: JWT token

#### Register (Admin only)

```http
POST /auth/register
```

Request body:

```json
{
  "email": "string",
  "username": "string",
  "password": "string",
  "role": "ADMIN|USER"
}
```

#### Change Password

```http
POST /auth/password
```

Request body:

```json
{
  "password": "string"
}
```

### Users

#### Get Current User

```http
GET /api/user
```

#### Get All Users (Admin only)

```http
GET /api/user/all
```

### Calls

#### Get User Calls

```http
GET /api/call
```

#### Get All Calls (Admin only)

```http
GET /api/call/all
```

#### Get Call by ID

```http
GET /api/call/{id}
```

#### Create Call

```http
POST /api/call
```

Request body:

```json
{
  "problem": "string",
  "solution": "string",
  "name": "string",
  "company": "string"
}
```

#### Update Call

```http
PUT /api/call/{id}
```

Request body: Same as Create Call

#### Partial Update Call

```http
PATCH /api/call/{id}
```

Request body: Any combination of Create Call fields

#### Delete Call

```http
DELETE /api/call/{id}
```

### Notes

#### Get User Notes

```http
GET /api/note
```

#### Get All Notes (Admin only)

```http
GET /api/note/all
```

#### Get Note by ID

```http
GET /api/note/{id}
```

#### Create Note

```http
POST /api/note
```

Request body:

```json
{
  "title": "string",
  "company": "string",
  "contact": "string",
  "situation": "string"
}
```

#### Update Note

```http
PUT /api/note/{id}
```

Request body: Same as Create Note

#### Partial Update Note

```http
PATCH /api/note/{id}
```

Request body: Any combination of Create Note fields

#### Delete Note

```http
DELETE /api/note/{id}
```

## Getting Started

1. Clone the repository
2. Configure PostgreSQL database in `application.properties`
3. Run the application:

```bash
./gradlew bootRun
```

## Docker Deployment

Run with Docker Compose:

```bash
docker-compose up -d
```

This will start both the PostgreSQL database and the Spring Boot application.

## Environment Variables

- `JWT_SECRET` - Secret key for JWT token generation
- `SPRING_DATASOURCE_URL` - Database URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password

## API Documentation

Access Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

## Default Admin User

The application creates a default admin user on startup:

- Email: admin@admin.com
- Password: admin1234
