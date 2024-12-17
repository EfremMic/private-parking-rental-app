package com.parking.payment.paymentservice.model;

public class PaymentRequest {

    private String token;  // Stripe token from frontend
    private int amount;    // Amount to charge in cents (e.g., $10 = 1000 cents)
    private String currency;
    private String description;

    public PaymentRequest() {
    }

    public PaymentRequest(String token, int amount, String currency, String description) {
        this.token = token;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    // Getters and Setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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