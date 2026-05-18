package com.livefxhub.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubscriptionService {

    private final Map<String, Set<String>> subscriptions =
            new HashMap<>();

    public void subscribe(String sessionId, String symbol) {

        subscriptions.putIfAbsent(symbol, new HashSet<>());

        subscriptions.get(symbol).add(sessionId);
    }
}