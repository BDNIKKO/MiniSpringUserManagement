Here’s a detailed README for your project based on the provided requirements:

---

# MiniSpringUserManagement

This is a Spring Boot application that implements user management functionalities, integrating validation, security, database connectivity, and exception handling. It supports two types of users: `ADMIN` and `USER`. `ADMIN` can view and manage all users, while `USER` can only manage their own profile.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Installation and Setup](#installation-and-setup)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [Security](#security)
- [Database Setup](#database-setup)
- [API Endpoints](#api-endpoints)
- [Exception Handling](#exception-handling)
- [Evaluation Criteria](#evaluation-criteria)
- [Bonus Points](#bonus-points)

---

## Features

- User registration with input validation.
- User authentication with role-based access control.
- Password hashing using `BCrypt`.
- Database connectivity using H2 in-memory database and Spring Data JPA.
- Role management (ADMIN and USER).
- Exception handling with meaningful error messages.
- JWT token-based authentication (Bonus).

---

## Requirements

- **Java JDK 11**
- **Maven**
- **Postman** or any API testing tool.
- **H2 Database** (In-memory database setup included).

---

## Installation and Setup

1. **Clone the repository**:

   ```bash
   git clone https://github.com/your-repository/MiniSpringUserManagement.git
   cd MiniSpringUserManagement
   ```

2. **Build the project**:

   ```bash
   mvn clean install
   ```

3. **Set up the H2 database** (No additional setup is needed for H2 as it is embedded).

4. **Configure the application properties**:

   Ensure the application uses the H2 database. The configuration should look like this:

   ```properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=password1234!
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

5. **Run the application**:

   ```bash
   mvn spring-boot:run
   ```

   The application will be accessible at `http://localhost:8080`.

---

## Running the Application

### Accessing the H2 Database

1. Navigate to the H2 console: `http://localhost:8080/h2-console`.
2. Enter the JDBC URL as `jdbc:h2:mem:testdb`.
3. The default username is `sa` and the password is `password123`.

---

## Usage

### Registration

- Users can register via the `/users/register` endpoint.
- You can test the registration with Postman using the following request:

  ```http
  POST http://localhost:8080/users/register
  Content-Type: application/json
  ```

  **Body** (JSON):

  ```json
  {
      "username": "user",
      "password": "password",
      "email": "user@example.com"
  }
  ```

### Authentication

- After registering, authenticate the user by making a POST request to `/authenticate`:

  ```http
  POST http://localhost:8080/authenticate
  Content-Type: application/json
  ```

  **Body** (JSON):

  ```json
  {
      "username": "testuser",
      "password": "complexPass123!"
  }
  ```

  A JWT token will be returned if authentication is successful.

### Accessing Secured Endpoints

Use the JWT token to access secured endpoints by adding it as a Bearer token in the Authorization header:

```http
Authorization: Bearer <your-jwt-token>
```

---

## Security

- The application uses **Spring Security** to manage authentication and authorization.
- Passwords are hashed using `BCrypt`.
- JWT (JSON Web Tokens) are used for stateless authentication (Bonus).

### Role-Based Access Control (RBAC)

- **USER**: Can register, log in, and manage their own profile.
- **ADMIN**: Can view and manage all users.

---

## Database Setup

- The application uses the **H2 in-memory database** for development purposes.
- No external database configuration is required for development.
- You can view and interact with the database through the H2 Console at `http://localhost:8080/h2-console`.

---

## API Endpoints

### Public Endpoints

- `POST /users/register` - Register a new user.
- `POST /authenticate` - Authenticate a user and receive a JWT token.

### Secured Endpoints (Require JWT)

- `GET /users/{id}` - Get details of a specific user (ADMIN can access all, USER can only access their own).
- `PUT /users/{id}` - Update user details (ADMIN can update any user, USER can only update their own).
- `DELETE /users/{id}` - Delete a user (Only accessible to ADMIN).

### Admin-Only Endpoints

- `GET /users` - View all users.

---

## Exception Handling

- **Global Exception Handling** is managed using `@ControllerAdvice`.
- Meaningful error messages are returned for validation and authentication errors.

---

## Evaluation Criteria

1. **Validation**:
    - User registration input validation with `@Valid` annotations.
    - Custom password strength validation.

2. **Security**:
    - Authentication and authorization using Spring Security.
    - Role-based access control for `ADMIN` and `USER`.

3. **Database Connectivity**:
    - Use of H2 in-memory database with Spring Data JPA.
    - CRUD operations for user management.

4. **Exception Handling**:
    - Proper handling of exceptions and returning meaningful error messages.

---

## Bonus Points

1. **JWT Integration**:
    - Stateless authentication using JSON Web Tokens (JWT).

---





