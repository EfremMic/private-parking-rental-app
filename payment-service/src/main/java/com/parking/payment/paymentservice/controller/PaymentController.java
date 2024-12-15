package com.parking.payment.paymentservice.controller;

import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.model.Payment;
import com.parking.payment.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Value("${stripe.publishable.key}")
    private String stripePublishableKey;

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Endpoint to fetch Stripe's publishable key for the frontend.
     */
    @GetMapping("/stripe-key")
    public ResponseEntity<String> getStripeKey() {
        logger.info("Fetching Stripe publishable key");
        return ResponseEntity.ok(stripePublishableKey);
    }

    /**
     * Handles the payment request.
     * - Charges the user via Stripe.
     * - Stores the payment details in the database.
     *
     * Request Body Example:
     * {
     *   "token": "tok_visa",
     *   "amount": 1000,
     *   "currency": "nok",
     *   "description": "Payment for parking spot",
     *   "userId": 1,
     *   "parkingSpotId": 2
     * }
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<PaymentResponse> chargePayment(@RequestBody PaymentRequest paymentRequest) {
        logger.info("Received payment request for amount: {}", paymentRequest.getAmount());
        try {
            PaymentResponse paymentResponse = paymentService.charge(paymentRequest);
            if ("succeeded".equals(paymentResponse.getStatus())) {
                logger.info("Payment succeeded for amount: {}", paymentRequest.getAmount());
                return ResponseEntity.ok(paymentResponse);
            } else {
                logger.warn("Payment failed with status: {}", paymentResponse.getStatus());
                return ResponseEntity.status(403).body(paymentResponse);
            }
        } catch (StripeException e) {
            logger.error("StripeException occurred: {}", e.getMessage());
            return ResponseEntity.status(500).body(new PaymentResponse(null, "failed", paymentRequest.getAmount(), paymentRequest.getCurrency()));
        }
    }

    /**
     * Retrieves all payment records.
     * Optionally, filter by userId.
     */
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments(@RequestParam(required = false) Long userId) {
        logger.info("Fetching payments for user: {}", userId);
        List<Payment> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }

}
