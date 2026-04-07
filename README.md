Readme · MD
Copy

# trade-ledger

Sistema de ingestão e processamento de dados de mercado em tempo real. Consome o WebSocket público da Binance, processa
eventos de trades e os disponibiliza via REST API.

Construído com Spring Boot, Apache Kafka e PostgreSQL em uma arquitetura de três microsserviços independentes.
 
---

## Arquitetura

```
Binance WebSocket
      │
      ▼
websocket-ingestor     → consome o stream e publica no Kafka
      │
      ▼
   Kafka
   tópico: raw-price-updates (3 partições, particionado por símbolo)
      │
      ▼
trade-processor        → consome do Kafka, normaliza e persiste no PostgreSQL
      │
      ▼
   PostgreSQL
      │
      ▼
market-data-api        → expõe os dados normalizados via REST
```

### Microsserviços

| Serviço              | Responsabilidade                                                  | Stack                                    |
|----------------------|-------------------------------------------------------------------|------------------------------------------|
| `websocket-ingestor` | Conecta ao WebSocket da Binance e publica eventos brutos no Kafka | Spring Boot, WebFlux, Kafka              |
| `trade-processor`    | Consome eventos do Kafka, normaliza e persiste no banco           | Spring Boot, Kafka, Spring Data JPA      |
| `market-data-api`    | Expõe os dados normalizados via REST                              | Spring Boot, Spring Web, Spring Data JPA |

### Pares monitorados

- BTCUSDT
- XRPUSDT
- BNBUSDT

---

## Tecnologias

- Java 21
- Spring Boot
- Spring WebFlux para o cliente WebSocket
- Spring Web
- Apache Kafka
- Spring Data JPA + Hibernate
- PostgreSQL
- Docker + Docker Compose
- Spring Boot Actuator