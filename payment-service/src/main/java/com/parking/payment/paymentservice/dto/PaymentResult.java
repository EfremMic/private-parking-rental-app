package com.parking.payment.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResult {
    private Long userId;         // Changed to Long
    private Long parkingSpotId;  // Changed to Long
    private boolean success;
    private String chargeId;
    private String errorMessage;
}
