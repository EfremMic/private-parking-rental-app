package com.parking.payment.paymentservice.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PaymentRequest {
    @NotNull(message = "Token cannot be null")
    @Size(min = 10, max = 255, message = "Token must be between 10 and 255 characters")
    private String token;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Long amount;

    @NotNull(message = "Currency cannot be null")
    @Size(min = 3, max = 3, message = "Currency must be exactly 3 characters")
    private String currency;

    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Parking Spot ID cannot be null")
    private Long parkingSpotId;

    // No-args constructor
    public PaymentRequest() {}

    // All-args constructor
    public PaymentRequest(String token, long amount, String currency, String description, Long userId, Long parkingSpotId) {
        this.token = token;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.userId = userId;
        this.parkingSpotId = parkingSpotId;
    }

    // Getters and setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getParkingSpotId() { return parkingSpotId; }
    public void setParkingSpotId(Long parkingSpotId) { this.parkingSpotId = parkingSpotId; }
}
