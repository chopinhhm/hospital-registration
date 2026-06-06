<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&color=0D1117&height=150&section=header&text=%E6%8C%82%E5%8F%B7%E7%B3%BB%E7%BB%9F&fontSize=36&fontColor=58A6FF&animation=fadeIn" />

[![Spring Cloud](https://img.shields.io/badge/Spring_Cloud_Alibaba-0079BE?style=flat-square&logo=spring&logoColor=white)]()
[![Redis Sentinel](https://img.shields.io/badge/Redis_Sentinel-DC382D?style=flat-square&logo=redis&logoColor=white)]()
[![Lua](https://img.shields.io/badge/Lua-000080?style=flat-square)]())
[![Sentinel](https://img.shields.io/badge/Sentinel-E60042?style=flat-square)]())

</div>

---

## ✨ Features

- 🔒 **三重分布式防重** — Redis Lua 原子操作 + DB 唯一索引 + 状态校验
- 🌊 **全链路流量治理** — Gateway 自定义过滤器 + Sentinel 规则
- 🏰 **高可用缓存** — Redis 哨兵集群 + 读写分离
- 📨 **异步解耦** — RabbitMQ 流量削峰 + 死信队列
- 🛡️ **故障隔离** — Feign 接口级容错策略

---

## 🔒 Distributed Anti-Duplication Design

\`\`\`
Request → Gateway Rate Limit
        → Service Layer Lua Script (SETNX + EXPIRE)
        → DB Unique Index (patient_id, schedule_id, date)
        → Business State Validation
        → Create Registration Order ✓
\`\`\`

**Three-Layer Protection:**
1. **Redis Lua Atomic** — Millisecond-level dedup
2. **Database Unique Index** — Final guarantee
3. **State Machine Check** — Prevent duplicate transitions

---

## 🚀 Quick Start

\`\`\`bash
git clone https://github.com/chopinhhm/hospital-registration.git
cd hospital-registration
docker-compose up -d
mvn spring-boot:run
\`\`\`

---

## 📄 License

MIT © [chopinhhm](https://github.com/chopinhhm)
