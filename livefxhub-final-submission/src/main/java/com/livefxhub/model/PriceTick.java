package com.livefxhub.model;

import lombok.Data;

@Data
public class PriceTick {
    private String symbol;
    private double buy;
    private double sell;
    private long timestamp;
}