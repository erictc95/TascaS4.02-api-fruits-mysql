# Fruit API - MySQL Version

## Description

Fruit API is a RESTful application developed with Spring Boot that allows managing fruits and their providers.

This version uses **MySQL** as the database and introduces a relationship between entities:

- A provider can supply multiple fruits.
- Each fruit belongs to one provider.

The project follows a layered architecture using Controllers, Services, Repositories, DTOs, Validation, Exception Handling, Docker, and MySQL.

---

## Technologies

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- MySQL 8
- Maven
- Docker
- Docker Compose
- Jakarta Validation

---

## Project Structure

```text
src
└── main
    ├── java
    │   └── cat.itacademy.s04.t02.n02.fruit
    │       ├── controller
    │       │   ├── FruitController.java
    │       │   └── ProviderController.java
    │       │
    │       ├── dto
    │       │   ├── CreateFruitRequest.java
    │       │   ├── UpdateFruitRequest.java
    │       │   ├── FruitResponse.java
    │       │   ├── CreateProviderRequest.java
    │       │   ├── UpdateProviderRequest.java
    │       │   └── ProviderResponse.java
    │       │
    │       ├── exception
    │       │   ├── FruitNotFoundException.java
    │       │   ├── ProviderNotFoundException.java
    │       │   └── GlobalExceptionHandler.java
    │       │
    │       ├── model
    │       │   ├── Fruit.java
    │       │   └── Provider.java
    │       │
    │       ├── repository
    │       │   ├── FruitRepository.java
    │       │   └── ProviderRepository.java
    │       │
    │       ├── service
    │       │   ├── FruitService.java
    │       │   ├── FruitServiceImpl.java
    │       │   ├── ProviderService.java
    │       │   └── ProviderServiceImpl.java
    │       │
    │       └── FruitApiMySqlApplication.java
    │
    └── resources
        └── application.properties

Dockerfile
docker-compose.yml
pom.xml
README.md
```

---

## Database Model

### Provider

| Field | Type |
|---------|---------|
| id | Long |
| name | String |
| country | String |

### Fruit

| Field | Type |
|---------|---------|
| id | Long |
| name | String |
| weightInKilos | int |
| provider | Provider |

### Relationship

```text
Provider (1)
    │
    │
    ▼
Fruit (N)
```

A provider can supply multiple fruits.

---

## API Endpoints

### Providers

#### Create Provider

```http
POST /providers
```

Request:

```json
{
  "name": "Fresh Fruits",
  "country": "Spain"
}
```

---

#### Get All Providers

```http
GET /providers
```

---

#### Get Provider By Id

```http
GET /providers/{id}
```

---

#### Update Provider

```http
PUT /providers/{id}
```

Request:

```json
{
  "name": "Fresh Fruits Updated",
  "country": "Spain"
}
```

---

#### Delete Provider

```http
DELETE /providers/{id}
```

Returns:

```http
204 No Content
```

Restrictions:

- Provider must exist.
- Provider cannot be deleted if it has associated fruits.

---

## Fruits

### Create Fruit

```http
POST /fruits
```

Request:

```json
{
  "name": "Apple",
  "weightInKilos": 10,
  "providerId": 1
}
```

---

### Get All Fruits

```http
GET /fruits
```

---

### Get Fruit By Id

```http
GET /fruits/{id}
```

---

### Filter Fruits By Provider

```http
GET /fruits?providerId=1
```

---

### Update Fruit

```http
PUT /fruits/{id}
```

Request:

```json
{
  "name": "Green Apple",
  "weightInKilos": 15,
  "providerId": 1
}
```

---

### Delete Fruit

```http
DELETE /fruits/{id}
```

Returns:

```http
204 No Content
```

---

## Validation Rules

### Provider

- Name cannot be blank.
- Country cannot be blank.
- Provider names must be unique.

### Fruit

- Name cannot be blank.
- Weight must be greater than zero.
- Provider ID is required.
- Provider must exist.

---

## Exception Handling

The application returns meaningful HTTP responses:

| Status | Description |
|----------|----------|
| 200 | Success |
| 201 | Resource created |
| 204 | Resource deleted |
| 400 | Validation or business rule error |
| 404 | Resource not found |

Examples:

```text
Provider with id: 999 not found
```

```text
Fruit with id: 999 not found
```

```text
Cannot delete provider because it has associated fruits
```

---

## Running with Docker

Build the image:

```bash
docker build -t fruit-api-mysql .
```

Run Docker Compose:

```bash
docker compose up -d
```

Stop containers:

```bash
docker compose down
```

---

## MySQL Configuration

Default database:

```text
Database: fruitdb
```

Default MySQL port:

```text
3310
```

Connection example:

```text
localhost:3310
```

---

## Author

Eric Tarrés Cabrisas
