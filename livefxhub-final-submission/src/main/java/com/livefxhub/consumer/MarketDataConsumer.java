package com.livefxhub.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.livefxhub.engine.PricingEngine;
import com.livefxhub.model.PriceTick;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MarketDataConsumer {

    private final PricingEngine pricingEngine;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void connect() {

        ReactorNettyWebSocketClient client =
                new ReactorNettyWebSocketClient();

        client.execute(
                URI.create("wss://quotes.livefxhub.com/?token=Lkj@asd@123&100"),
                session -> session.receive()
                        .map(message -> message.getPayloadAsText())
                        .doOnNext(this::handleIncoming)
                        .then()
        ).retryWhen(
                Retry.backoff(10, Duration.ofSeconds(2))
        ).subscribe();
    }

    private void handleIncoming(String json) {

        try {

            JsonNode node = mapper.readTree(json);

            PriceTick tick = new PriceTick();

            tick.setSymbol(node.get("symbol").asText());
            tick.setBuy(node.get("buy").asDouble());
            tick.setSell(node.get("sell").asDouble());
            tick.setTimestamp(System.currentTimeMillis());

            pricingEngine.processTick(tick);

        } catch (Exception e) {
            System.out.println("Failed to process tick");
        }
    }
}