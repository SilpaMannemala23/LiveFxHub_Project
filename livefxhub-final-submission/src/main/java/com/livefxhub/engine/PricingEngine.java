package com.livefxhub.engine;

import com.livefxhub.model.PriceTick;
import com.livefxhub.model.SymbolState;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class PricingEngine {

    private final Map<String, ConcurrentLinkedDeque<PriceTick>> tickStore =
            new ConcurrentHashMap<>();

    private final Map<String, SymbolState> marketState =
            new ConcurrentHashMap<>();

    private static final long WINDOW =
            24 * 60 * 60 * 1000L;

    public void processTick(PriceTick tick) {

        tickStore.putIfAbsent(
                tick.getSymbol(),
                new ConcurrentLinkedDeque<>()
        );

        marketState.putIfAbsent(
                tick.getSymbol(),
                new SymbolState()
        );

        ConcurrentLinkedDeque<PriceTick> deque =
                tickStore.get(tick.getSymbol());

        deque.addLast(tick);

        long cutoff = System.currentTimeMillis() - WINDOW;

        while (!deque.isEmpty()
                && deque.peekFirst().getTimestamp() < cutoff) {

            deque.pollFirst();
        }

        double high = Double.MIN_VALUE;
        double low = Double.MAX_VALUE;

        for (PriceTick t : deque) {
            high = Math.max(high, t.getBuy());
            low = Math.min(low, t.getBuy());
        }

        SymbolState state = marketState.get(tick.getSymbol());

        state.setLatestBuy(tick.getBuy());
        state.setLatestSell(tick.getSell());
        state.setHigh24h(high);
        state.setLow24h(low);

        if (!deque.isEmpty()) {

            double oldest = deque.peekFirst().getBuy();

            double change =
                    ((tick.getBuy() - oldest) / oldest) * 100;

            state.setChange24h(change);
        }
    }

    public Map<String, SymbolState> getMarketState() {
        return marketState;
    }
}