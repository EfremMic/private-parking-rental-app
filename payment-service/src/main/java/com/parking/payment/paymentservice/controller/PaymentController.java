package com.parking.payment.paymentservice.controller;

import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        try {
            Map<String, String> paymentIntent = paymentService.createPaymentIntent(paymentRequest);
            return ResponseEntity.ok(paymentIntent);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "PaymentIntent creation failed"));
        }
    }
}
