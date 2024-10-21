package com.parking.payment.paymentservice.dto;



public class PaymentRequest {
    private String token;
    private long amount;
    private String currency;
    private String description;

    // Constructor
    public PaymentRequest(String token, long amount, String currency, String description) {
        this.token = token;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getAmount() {
        return amount;
    }

public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}