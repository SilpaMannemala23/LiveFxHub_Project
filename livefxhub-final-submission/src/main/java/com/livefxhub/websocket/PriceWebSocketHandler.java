package com.livefxhub.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class PriceWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        Flux<String> flux = Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> {

                    try {

                        DummyData data = new DummyData();

                        double base = 1.0840 + Math.random() * 0.010;

                        data.symbol = "EURUSD";

                        data.buy = Math.round(base * 100000.0) / 100000.0;
                        data.sell = Math.round((base + 0.0002) * 100000.0) / 100000.0;

                        data.high24h = Math.round((base + 0.005) * 100000.0) / 100000.0;
                        data.low24h = Math.round((base - 0.005) * 100000.0) / 100000.0;

                        data.change24h =
                                Math.round((Math.random() * 2 - 1) * 100.0) / 100.0;

                        return mapper.writeValueAsString(data);

                    } catch (Exception e) {

                        return "{}";
                    }
                });

        return session.send(
                flux.map(session::textMessage)
        );
    }

    static class DummyData {

        public String symbol;
        public double buy;
        public double sell;
        public double high24h;
        public double low24h;
        public double change24h;
    }
}
