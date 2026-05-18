package com.livefxhub.config;

import com.livefxhub.websocket.PriceWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class WebSocketConfig {

    private final PriceWebSocketHandler handler;

    @Bean
    public HandlerMapping webSocketMapping() {

        Map<String, Object> map = new HashMap<>();

        map.put("/ws/prices", handler);

        return new SimpleUrlHandlerMapping(map, -1);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}