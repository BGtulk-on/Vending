package com.coffee.model;
import java.util.Map;

public class OrderResponse {
    public String message;
    public double changeAmount;
    public Map<String, Integer> changeBreakdown;

    public OrderResponse(String message, double changeAmount, Map<String, Integer> changeBreakdown) {
        this.message = message;
        this.changeAmount = changeAmount;
        this.changeBreakdown = changeBreakdown;
    }
}