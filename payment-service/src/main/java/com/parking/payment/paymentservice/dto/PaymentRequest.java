package com.parking.payment.paymentservice.dto;
import lombok.AllArgsConstructor;
/*


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

}*/



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private String token;  // Stripe token from frontend
    private Long amount;    // Amount to charge in cents (e.g., $10 = 1000 cents)
    private String currency;
    private String description;
    private Long userId;   // ID of the user making the payment
    private Long parkingSpotId; // ID of the parking spot being rented
}
