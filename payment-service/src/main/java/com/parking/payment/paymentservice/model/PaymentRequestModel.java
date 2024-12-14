package com.parking.payment.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestModel {
    private String token;  // Stripe token from frontend
    private int amount;    // Amount to charge in cents (e.g., $10 = 1000 cents)
    private String currency;
    private String description;
    private Long userId;   // ID of the user making the payment
    private Long parkingSpotId; // ID of the parking spot being rented
}
