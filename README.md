# trade-ledger

Pipeline de ingestão de dados de mercado em tempo real. Consome o WebSocket público de trades da Binance e persiste cada evento no PostgreSQL, com o Kafka desacoplando a captura do stream da gravação no banco.

São dois serviços Spring Boot independentes, comunicando-se apenas por Kafka — nenhum conhece o outro diretamente. O ponto do projeto é praticar um fluxo de streaming desacoplado: um lado não pode derrubar nem pressionar o outro, e o broker absorve picos e reconexões.

## Arquitetura

```
Binance WebSocket (btcusdt / bnbusdt / xrpusdt @trade)
      │
      ▼
socketingestor      cliente WebSocket reativo → produtor Kafka
      │
      ▼
Kafka  (tópico: trade.market-data, chave = símbolo)
      │
      ▼
tradeprocessor      consumidor Kafka → persiste no PostgreSQL
      │
      ▼
PostgreSQL (tabela raw_trades)
```

| Módulo | Responsabilidade | Stack |
|---|---|---|
| `socketingestor` | Mantém a conexão com o WebSocket da Binance e publica cada mensagem crua no Kafka | Spring Boot, WebFlux, Kafka |
| `tradeprocessor` | Consome do Kafka, desserializa o payload da Binance em colunas tipadas e grava no banco | Spring Boot, Spring Kafka, Spring Data JPA |

## Decisões de arquitetura

- **Kafka como fronteira entre captura e persistência.** O `socketingestor` só produz; o `tradeprocessor` só consome. Se o banco cair ou ficar lento, as mensagens acumulam no tópico em vez de se perderem no stream — a Binance não espera ninguém.

- **Ordenação por símbolo.** Cada mensagem é publicada com o **símbolo como chave** (`BTCUSDT`, etc.), então todos os trades de um mesmo par caem na mesma partição e são consumidos em ordem. O produtor reforça isso com `max.in.flight.requests.per.connection = 1` e `retries = 1`, evitando reordenação em caso de reenvio. É uma garantia de ordem *por par*, não global.

- **Reconexão resiliente no ingestor.** O cliente WebSocket (Reactor Netty) reconecta indefinidamente com *backoff* — 5s de intervalo fixo, teto de 2 min — e faz `repeat()` ao fechar. Uma queda de conexão da Binance não derruba o serviço.

- **O ingestor não interpreta o payload.** Ele só extrai o campo de símbolo (para a chave Kafka) e publica o JSON **cru**. Toda a desserialização acontece no `tradeprocessor`, que mapeia os campos da Binance (`e`, `E`, `s`, `t`, `p`, `q`, `T`, `m`) para a entidade `MarketTrade` e persiste em `raw_trades`. Preço e quantidade usam `BigDecimal(18,8)` para não perder precisão.

## Infraestrutura

Cada serviço traz seu próprio `docker-compose`: o `socketingestor` sobe Kafka (+ Zookeeper e Kafka UI), o `tradeprocessor` sobe o PostgreSQL. Kafka exposto em `29092`, Postgres em `5432`, Kafka UI em `8090`.

## Stack

Java 21 · Spring Boot 4.0.5 · Spring WebFlux (cliente WebSocket reativo) · Spring Kafka · Spring Data JPA / Hibernate · PostgreSQL · Docker Compose · Spring Boot Actuator.

## Estado atual

**Funcionando de ponta a ponta:** captura do WebSocket, publicação no Kafka com ordenação por símbolo, consumo e persistência dos trades crus no PostgreSQL.

**Trabalho em aberto:**

- **Microsserviço de relatórios em Python (planejado, ainda não implementado).** O projeto será um monorepo poliglota: além do Java, um serviço em Python será responsável por montar relatórios com gráficos e enviá-los por e-mail. Python foi escolhido pelo ecossistema de geração de relatórios (pandas, matplotlib, WeasyPrint), maduro e mais direto que os equivalentes na JVM. O serviço lerá os dados **diretamente do banco, sem passar pelo Kafka** — assim o pipeline de ingestão não é afetado pela geração de relatórios, que é uma carga pesada e de execução assíncrona.
