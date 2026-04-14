<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&color=0:00ffc8,100:0080ff&height=200&section=header&text=NexaVerse&fontSize=80&fontColor=ffffff&animation=fadeIn&fontAlignY=38&desc=AI-Powered%20Metaverse%20Platform&descAlignY=60&descSize=20" width="100%"/>

[![Java](https://img.shields.io/badge/Java-21-FF6B35?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://react.dev/)
[![Three.js](https://img.shields.io/badge/Three.js-3D-000000?style=for-the-badge&logo=threedotjs&logoColor=white)](https://threejs.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://postgresql.org/)
[![Redis](https://img.shields.io/badge/Redis-Cache-DC382D?style=for-the-badge&logo=redis&logoColor=white)](https://redis.io/)
[![Kafka](https://img.shields.io/badge/Kafka-Events-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)](https://kafka.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://docker.com/)

> 🌌 **Discord + Roblox + ChatGPT** — combined into one immersive platform

</div>

---

## 🎯 What is NexaVerse?

**NexaVerse** is a production-grade full-stack metaverse platform where users can:
- 🌍 Explore **5 immersive 3D worlds** (Forest, City, Desert, Ocean, Dungeon)
- 🤖 Talk to **AI companions** (MIMIR & GUANYIN) using **voice commands**
- 👥 Meet other players in **real-time multiplayer** environments
- 🏆 Compete on a **Redis-powered leaderboard** with achievements
- 🔍 Discover worlds via **semantic AI search**
- 📊 Monitor the system with a **live JVM dashboard**

---

## ⚡ Tech Stack

| Layer | Technologies |
|-------|-------------|
| **Backend** | Java 21, Spring Boot 3.5, Spring Security, Spring AI |
| **Database** | PostgreSQL 17, Redis (Cache + ZSet Leaderboard) |
| **Messaging** | Apache Kafka (Async events) |
| **Real-time** | WebSocket + STOMP protocol |
| **AI/LLM** | Groq API + LLaMA 3.3 70B |
| **Frontend** | React 18, Vite, Three.js, React Three Fiber |
| **3D/Visual** | @react-three/drei, Zustand, Web Speech API |
| **DevOps** | Docker Compose, GitHub Actions CI/CD |

---

## 🌟 Key Features

### 🌍 5 Immersive 3D Worlds
| World | Description |
|-------|-------------|
| 🌲 Dark Forest | Glowing mushrooms, fireflies, sparkles, procedural terrain |
| 🏙️ Neon City | Cyberpunk skyscrapers, rain, neon lights |
| 🏜️ Desert Ruins | Sand dunes, cacti, rock formations |
| 🌊 Deep Ocean | Bioluminescent coral, wave effects |
| 🏰 Dark Dungeon | Gothic pillars, purple crystal flames |

### 🤖 AI Companions
- **MIMIR** — Norse god of wisdom inspired by God of War
- **GUANYIN** — Goddess of mercy inspired by Black Myth: Wukong
- 🎙️ Voice input via **Web Speech API**
- 🔊 Voice output via **Speech Synthesis API**
- 🧠 Powered by **Groq + LLaMA 3.3 70B**
- Companion **follows your avatar** in the 3D world

### 🏆 Redis Leaderboard
- **O(log n)** sorted set operations
- Real-time score updates
- 8 unlockable achievements
- Player rank tracking

### 📡 Real-time Multiplayer
- **WebSocket + STOMP** protocol
- Live avatar movement sync
- World chat system
- Player presence tracking

---

## 🏗️ System Architecture
┌─────────────────────────────────────────────────────┐
│                   CLIENT (React)                     │
│   Three.js 3D  │  WebSocket  │  REST API  │  Voice  │
└────────────────────────┬────────────────────────────┘
│
┌────────────────────────▼────────────────────────────┐
│              Spring Boot 3.5 Backend                 │
│   JWT Auth  │  REST APIs  │  WebSocket  │  Kafka     │
└──────┬───────────┬──────────────┬───────────┬───────┘
│           │              │           │
PostgreSQL     Redis          Kafka      Groq API
(Data)     (Cache+LB)     (Events)    (AI/LLM)

---

## 🚀 Quick Start

### With Docker

```bash
git clone https://github.com/yashpratap-dev/nexaverse.git
cd nexaverse

echo "GROQ_API_KEY=your_key_here" > .env
echo "JWT_SECRET=your_secret_here" >> .env

docker-compose up --build -d
```

Open `http://localhost:3000` 🚀

### Local Development

```bash
# Backend
cd nexaverse
./mvnw spring-boot:run

# Frontend
cd nexaverse/nexaverse-frontend
npm install && npm run dev
```

---

## 📡 API Reference

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/api/auth/register` | Register new user | ❌ |
| `POST` | `/api/auth/login` | Login + JWT token | ❌ |
| `GET` | `/api/worlds` | Get all worlds | ✅ |
| `GET` | `/api/avatars` | Get user avatars | ✅ |
| `POST` | `/api/ai/chat/{userId}` | Chat with AI companion | ✅ |
| `GET` | `/api/leaderboard/top` | Top 10 players | ❌ |
| `GET` | `/api/leaderboard/rank/{username}` | Player rank | ❌ |
| `GET` | `/api/search/worlds?query=` | Semantic world search | ❌ |
| `GET` | `/api/monitor/system` | Live JVM metrics | ❌ |

---

## 🧠 CS & DSA Concepts

| Concept | Implementation |
|---------|---------------|
| **ZSet** | Redis O(log n) leaderboard |
| **Token Bucket** | Custom rate limiter |
| **Observer Pattern** | WebSocket events |
| **Factory Pattern** | Avatar type creation |
| **Strategy Pattern** | AI companion selection |
| **Event-Driven** | Kafka async pipeline |
| **JWT Security** | HS512 stateless auth |

---

## 🐳 Docker Services
nexaverse-postgres   → PostgreSQL 17    :5432
nexaverse-redis      → Redis latest     :6379
nexaverse-kafka      → Apache Kafka     :9092
nexaverse-backend    → Spring Boot      :8080
nexaverse-frontend   → React + Nginx    :3000

---

## 👨‍💻 Developer

<div align="center">

**Yash Pratap**

[![GitHub](https://img.shields.io/badge/GitHub-yashpratap--dev-181717?style=for-the-badge&logo=github)](https://github.com/yashpratap-dev)

*Full-stack Java Developer | CS Student | Competitive Programmer*

> *"Built NexaVerse to demonstrate production-grade enterprise Java development, real-time systems, AI integration, and immersive 3D web experiences."*

</div>

---

<div align="center">
<img src="https://capsule-render.vercel.app/api?type=waving&color=0:0080ff,100:00ffc8&height=100&section=footer" width="100%"/>

⭐ **Star this repo if you found it interesting!** ⭐
</div>