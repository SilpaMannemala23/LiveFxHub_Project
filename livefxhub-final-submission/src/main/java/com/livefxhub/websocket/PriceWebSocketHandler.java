package com.livefxhub.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.livefxhub.engine.PricingEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class PriceWebSocketHandler implements WebSocketHandler {

    private final PricingEngine pricingEngine;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public reactor.core.publisher.Mono<Void> handle(WebSocketSession session) {

        Flux<String> stream = Flux.interval(Duration.ofMillis(1000))
                .map(tick -> {
                    try {
                        return mapper.writeValueAsString(
                                pricingEngine.getMarketState()
                        );
                    } catch (Exception e) {
                        return "{}";
                    }
                });

        return session.send(
                stream.map(session::textMessage)
        );
    }
}