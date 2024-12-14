package com.parking.payment.paymentservice.controller;

import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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

    // Expose the publishable key to the frontend
    @GetMapping("/stripe-key")
    public ResponseEntity<String> getStripeKey() {
        logger.info("Fetching Stripe publishable key");
        return ResponseEntity.ok(stripePublishableKey);
    }

    @PostMapping("/charge")
    public ResponseEntity<PaymentResponse> chargePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
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
}

/*
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/charge")
    public ResponseEntity<PaymentResponse> chargePayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentResponse paymentResponse = paymentService.charge(paymentRequest);
            return ResponseEntity.ok(paymentResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
*/

/*
import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/charge")
    public ResponseEntity<PaymentResponse> chargePayment(@RequestBody PaymentRequest paymentRequest) {
        System.out.println("Payment request received: " + paymentRequest);

        // Extract fields from the request
        String token = paymentRequest.getToken();
        long amount = paymentRequest.getAmount();
        String currency = paymentRequest.getCurrency();
        String description = paymentRequest.getDescription();

        if (token == null || amount <= 0 || currency == null || description == null) {
            return ResponseEntity.badRequest().body(new PaymentResponse(null, "failed", amount, currency));
        }

        try {
            // Process payment using PaymentService
            PaymentResponse paymentResponse = paymentService.charge(paymentRequest);

            if ("succeeded".equals(paymentResponse.getStatus())) {
                return ResponseEntity.ok(paymentResponse);
            } else {
                return ResponseEntity.status(403).body(paymentResponse);
            }
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new PaymentResponse(null, "failed", amount, currency));
        }
    }


}
*/

/*****************************************************
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/charge")
    public ResponseEntity<PaymentResponse> chargePayment(@RequestBody PaymentRequest paymentRequest) {
        System.out.println("Payment request received: " + paymentRequest);

        // Validate request data
        String token = paymentRequest.getToken();
        long amount = paymentRequest.getAmount();
        String currency = paymentRequest.getCurrency();
        String description = paymentRequest.getDescription();

        if (token == null || amount <= 0 || currency == null || description == null) {
            return ResponseEntity.badRequest().body(new PaymentResponse(null, "failed", amount, currency));
        }

        try {
            // Process payment using PaymentService
            PaymentResponse paymentResponse = paymentService.charge(paymentRequest);

            if ("succeeded".equals(paymentResponse.getStatus())) {
                return ResponseEntity.ok(paymentResponse);
            } else {
                return ResponseEntity.status(403).body(paymentResponse);
            }
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new PaymentResponse(null, "failed", amount, currency));
        }
    }
}
*/