package com.parking.payment.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private String chargeId;  // Stripe Charge ID
    private String status;    // Payment status (e.g., "succeeded")
    private Long amount;      // Amount charged in cents (use Long to match Stripe's API)
    private String currency;  // Currency (e.g., "nok")
}
