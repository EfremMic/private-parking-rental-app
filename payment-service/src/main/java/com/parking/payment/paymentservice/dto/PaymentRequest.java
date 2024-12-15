package com.parking.payment.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String token;            // Stripe token
    private Long amount;             // Payment amount in cents
    private String currency;         // Currency (e.g., "nok")

    private String description;      // Payment description
    private Long userId;             // User ID
    private Long parkingSpotId;      // Parking Spot ID


}
