package com.parking.payment.paymentservice.controller;

import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/charge")
    public PaymentResponse chargeCard(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        // Call the service to process the payment and return a PaymentResponse
        return paymentService.charge(paymentRequest);
    }
}