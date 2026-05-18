
# Live Fx Hub - Real-Time Pricing Engine

## Overview

Low-latency market data processing system built using Spring Boot and WebFlux.

## Features

- Live WebSocket feed consumer
- Rolling 24h calculations
- Reactive WebSocket broadcasting
- Redis persistence structure
- Dockerized deployment
- Fault-tolerant reconnect logic
- Subscription service foundation

## Architecture

External Feed -> Pricing Engine -> Redis -> WebSocket Server -> Clients

## Tech Stack

- Java 21
- Spring Boot
- Spring WebFlux
- Redis
- Docker

## Run

```bash
mvn clean install
mvn spring-boot:run
```

OR

```bash
docker-compose up
```

## WebSocket Endpoint

```text
ws://localhost:8080/ws/prices
```

## Design Decisions

- Reactive programming for low latency
- ConcurrentHashMap for thread safety
- Rolling 24h calculations using deque
- Dockerized deployment for portability

## Trade-offs

| Decision | Benefit | Trade-off |
|---|---|---|
| Reactive model | Better scalability | More complexity |
| Redis | Recovery support | Extra infrastructure |
| Delta updates | Smaller payloads | More client logic |

## Challenges

- Handling real-time data efficiently
- Maintaining rolling 24h calculations
- Keeping latency low
- Designing scalable websocket broadcasting

## Future Improvements

- Binary protocol support
- Advanced delta compression
- Metrics dashboard
- Full Redis persistence
- Enhanced out-of-order tick handling
