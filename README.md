# Task Management REST API

A RESTful API for managing tasks, built with Spring Boot. Supports creating, reading, updating, and deleting tasks with status tracking and priority levels.

## Tech Stack

- **Java 17**
- **Spring Boot 4.0.6**
- **Spring Web** — REST layer
- **Spring Validation** — request validation
- **Lombok** — boilerplate reduction
- **JUnit 5 + Mockito** — unit testing
- **Maven** — build tool

> Data is stored in-memory (no database required to run).

## Project Structure

```
src/main/java/com/apiartem/taskmanager/
├── controller/
│   └── TaskController.java       # REST endpoints
├── service/
│   └── TaskService.java          # Business logic
├── repository/
│   └── TaskRepository.java       # In-memory data store
├── model/
│   ├── Task.java                 # Entity
│   ├── TaskStatus.java           # Enum: TODO, IN_PROGRESS, DONE
│   └── TaskPriority.java         # Enum: LOW, MEDIUM, HIGH
├── dto/
│   ├── TaskRequest.java          # Incoming request body
│   ├── TaskResponse.java         # Outgoing response body
│   └── UpdateStatusRequest.java  # PATCH status body
└── exception/
    ├── TaskNotFoundException.java
    ├── GlobalExceptionHandler.java
    └── ErrorResponse.java
```

## Getting Started

**Prerequisites:** Java 17+, Maven 3.6+

```bash
git clone https://github.com/your-username/taskmanager.git
cd taskmanager
./mvnw spring-boot:run
```

The server starts on `http://localhost:8080`.

## API Reference

### Create a task
```
POST /tasks
```
```json
{
  "title": "Fix login bug",
  "description": "Users can't log in with Google OAuth",
  "status": "TODO",
  "priority": "HIGH"
}
```
Returns `201 Created`.

---

### Get all tasks
```
GET /tasks
```

Optional query parameters for filtering:
```
GET /tasks?status=TODO
GET /tasks?priority=HIGH
GET /tasks?status=IN_PROGRESS&priority=MEDIUM
```

---

### Search tasks by keyword
```
GET /tasks/search?keyword=bug
```
Searches across both `title` and `description` fields.

---

### Get task by ID
```
GET /tasks/{id}
```

---

### Update a task
```
PUT /tasks/{id}
```
```json
{
  "title": "Fix login bug",
  "description": "Resolved — was a token expiry issue",
  "status": "DONE",
  "priority": "HIGH"
}
```

---

### Update status only
```
PATCH /tasks/{id}/status
```
```json
{
  "status": "IN_PROGRESS"
}
```

---

### Delete a task
```
DELETE /tasks/{id}
```
Returns `204 No Content`.

---

## Enums

**TaskStatus**
| Value | Description |
|---|---|
| `TODO` | Not started |
| `IN_PROGRESS` | Currently being worked on |
| `DONE` | Completed |

**TaskPriority**
| Value | Description |
|---|---|
| `LOW` | Can wait |
| `MEDIUM` | Normal priority |
| `HIGH` | Needs attention soon |

---

## Error Handling

All errors return a consistent JSON structure:

```json
{
  "status": 404,
  "message": "Task with id 5 not found",
  "timestamp": "2025-05-10T17:04:00"
}
```

Validation errors return `400` with a breakdown per field:

```json
{
  "status": 400,
  "error": "Validation failed",
  "fields": {
    "title": "Title must not be blank",
    "priority": "Priority is required"
  },
  "timestamp": "2025-05-10T17:04:00"
}
```

| HTTP Status | When |
|---|---|
| `201 Created` | Task successfully created |
| `204 No Content` | Task successfully deleted |
| `400 Bad Request` | Validation failed |
| `404 Not Found` | Task ID does not exist |
| `500 Internal Server Error` | Unexpected server error |

---

## Running Tests

```bash
./mvnw test
```

Tests cover the service layer using Mockito, including both success paths and exception scenarios.
