# NexaVerse 🌐

AI-Powered Metaverse Social Platform built with Java 25 + Spring Boot 3.5

## Tech Stack
- **Backend**: Java 25, Spring Boot 3.5
- **Database**: PostgreSQL 17
- **ORM**: Hibernate + Spring Data JPA
- **Real-time**: WebSocket (coming Week 5)
- **AI**: Spring AI + RAG (coming Week 8)

## Features Built
- User entity with JPA
- Avatar system — HumanAvatar + BotAvatar (OOP)
- BFS Pathfinding algorithm (DSA)
- System monitoring API (Linux metrics)
- Virtual Threads enabled (Java 21 Loom)

## API Endpoints
| Endpoint | Description |
|---|---|
| GET /hello | Health check |
| GET /api/test/all | Java 21 features |
| GET /api/oop/avatars | Avatar system |
| GET /api/oop/pathfinding | BFS pathfinding |
| GET /api/monitor/system | Live system metrics |

## Setup
1. Clone: `git clone https://github.com/yashpratap-dev/nexaverse.git`
2. Copy `application.properties.example` → `application.properties`
3. Fill in your DB credentials
4. Run: `./mvnw spring-boot:run`
5. Open: `localhost:8080/hello`

## Project Structure
```
src/main/java/com/nexaverse/nexaverse/
├── controller/   — REST APIs
├── entity/       — JPA entities
├── model/        — Java 21 Records + Sealed classes
├── service/      — Business logic
└── repository/   — DB operations
```

## Roadmap
- [x] Week 1 — Setup + Java 21 + OOP + DSA
- [ ] Week 2 — REST APIs + Validation
- [ ] Week 3 — JWT Auth
- [ ] Week 5 — WebSocket Real-time
- [ ] Week 8 — Spring AI + RAG
- [ ] Week 12 — Deploy
