package com.livefxhub.model;

import lombok.Data;

@Data
public class SymbolState {

    private double latestBuy;
    private double latestSell;
    private double high24h;
    private double low24h;
    private double change24h;
}