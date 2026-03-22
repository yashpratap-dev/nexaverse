# NexaVerse 🌐

AI-Powered Metaverse Platform Backend

## Tech Stack
- **Backend**: Java 25 + Spring Boot 3.5.11
- **Database**: PostgreSQL 17
- **Cache**: Redis
- **Messaging**: Apache Kafka
- **Real-time**: WebSocket (STOMP)
- **AI**: Groq API (Llama 3.3-70b) — Free
- **Security**: JWT + BCrypt + Spring Security
- **Docs**: Swagger UI

## Features
- 🔐 JWT Authentication + BCrypt
- 🌍 Virtual World Management
- 👤 Avatar System (20+ types planned)
- ⚡ Real-time Movement (WebSocket + Kafka)
- 💬 Real-time Chat System
- 🤖 AI Companions — MIMIR & GUANYIN
- ⚔️ AI Quest Generator
- 🎤 Voice Command Processing
- 🔍 Semantic World Search + Recommendations
- 📊 Redis Caching
- 📨 Kafka Event Pipeline
- 🛡️ Rate Limiting (100 req/min)
- 📝 Structured Logging

## Run with Docker
```bash
docker-compose up -d
```

## API Docs
```
localhost:8080/swagger-ui/index.html
```

## Setup (Local)
1. Clone: `git clone https://github.com/yashpratap-dev/nexaverse.git`
2. Add `application.properties` with your DB + Groq API key
3. Start Redis + Kafka
4. Run: `./mvnw spring-boot:run`

## Roadmap
- [x] Week 1 — Java 25 + OOP + DSA + Setup
- [x] Week 2 — REST APIs + JWT + Security
- [x] Week 3 — Redis + Kafka + WebSocket
- [x] Week 4 — AI Companions + Quest + Voice
- [x] Week 5 — Testing + Rate Limiting + Docker
- [ ] Week 6 — Deploy (Railway/Render)
- [ ] Week 7 — React Frontend (Apple-level UI)
- [ ] Week 8 — 3D Avatars (Three.js + Mixamo)
- [ ] Week 9 — Fighting Arena (PvP)
- [ ] Week 10 — Voice AI Commands
- [ ] Week 12 — Final Deploy + Portfolio