package com.parking.payment.paymentservice.service;


import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public PaymentResponse charge(PaymentRequest paymentRequest) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentRequest.getAmount());
        chargeParams.put("currency", paymentRequest.getCurrency());
        chargeParams.put("source", paymentRequest.getToken()); // Stripe token
        chargeParams.put("description", paymentRequest.getDescription());

        // Create the charge using Stripe's API
        Charge charge = Charge.create(chargeParams);

        // Convert the Charge object into a PaymentResponse
        return new PaymentResponse(
                charge.getId(),
                charge.getStatus(),
                charge.getAmount(),
                charge.getCurrency()
        );
    }

}