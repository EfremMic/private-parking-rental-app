package com.parking.payment.paymentservice.dto;

public class PaymentResponse {
    private String chargeId;
    private String status;
    private long amount;
    private String currency;

    // Constructor
    public PaymentResponse(String chargeId, String status, long amount, String currency) {
        this.chargeId = chargeId;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
    }

    // Getters and setters
    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}