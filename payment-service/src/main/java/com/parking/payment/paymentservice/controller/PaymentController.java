package com.parking.payment.paymentservice.controller;

import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service is healthy");
    }
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    /**
     * Endpoint to charge the user's card.
     *
     * @param paymentRequest the payment request payload
     * @return ResponseEntity with payment response
     * @throws StripeException if payment processing fails
     */
    @PostMapping("/charge")
    public ResponseEntity<PaymentResponse> chargeCard(@Valid @RequestBody PaymentRequest paymentRequest) {
        log.info("Received charge request for userId: {}, parkingSpotId: {}",
                paymentRequest.getUserId(), paymentRequest.getParkingSpotId());
        try {
            PaymentResponse paymentResponse = paymentService.charge(paymentRequest);
            log.info("Payment successful for userId: {}", paymentRequest.getUserId());
            return ResponseEntity.ok(paymentResponse);
        } catch (StripeException ex) {
            log.error("Payment failed: {}", ex.getMessage());
            return ResponseEntity.status(500).body(
                    new PaymentResponse(
                            "FAILED",
                            null,
                            0,
                            "Payment failed: " + ex.getMessage()
                    )
            );
        }
    }


    /**
     * Endpoint to create a PaymentIntent for Stripe integration.
     *
     * @param paymentRequest the payment request payload
     * @return ResponseEntity with clientSecret for Stripe
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        log.info("Received PaymentIntent creation request for userId: {}", paymentRequest.getUserId());
        Map<String, String> response = new HashMap<>();
        try {
            // Replace "your-client-secret" with dynamic generation logic
            String clientSecret = "your-client-secret"; // Simulate Stripe PaymentIntent clientSecret
            response.put("clientSecret", clientSecret);
            log.info("PaymentIntent created successfully for userId: {}", paymentRequest.getUserId());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Failed to create PaymentIntent: {}", ex.getMessage());
            response.put("error", "Failed to create PaymentIntent");
            return ResponseEntity.status(500).body(response);
        }
    }
}
