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

/*
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

}*/

import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResult;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${payment.result.queue.name}")
    private String paymentResultQueue;

    private final RabbitTemplate rabbitTemplate;

    public PaymentService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        try {
            Stripe.apiKey = stripeApiKey;
            System.out.println("Stripe API successfully initialized");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Stripe API: " + e.getMessage());
        }
    }

    /**
     * Handles incoming payment requests from RabbitMQ.
     * @param paymentRequest PaymentRequest DTO containing payment details.
     */
    @RabbitListener(queues = "${payment.request.queue.name}")
    public void handlePaymentRequest(PaymentRequest paymentRequest) {
        System.out.println("Received payment request: " + paymentRequest);
        try {
            // Process the payment
            PaymentResponse paymentResponse = charge(paymentRequest);

            // Log success and publish result to RabbitMQ
            System.out.println("Payment successful: " + paymentResponse);
            PaymentResult paymentResult = new PaymentResult(
                    paymentRequest.getUserId(),
                    paymentRequest.getParkingSpotId(),
                    true,
                    paymentResponse.getChargeId(),
                    null // No error message for successful payment
            );
            rabbitTemplate.convertAndSend(paymentResultQueue, paymentResult);
        } catch (StripeException e) {
            // Log failure and publish error result to RabbitMQ
            System.err.println("Payment processing failed: " + e.getMessage());
            PaymentResult paymentResult = new PaymentResult(
                    paymentRequest.getUserId(),
                    paymentRequest.getParkingSpotId(),
                    false,
                    null, // No charge ID for failed payment
                    e.getMessage() // Include the error message
            );
            rabbitTemplate.convertAndSend(paymentResultQueue, paymentResult);
        }
    }

    /**
     * Charges a payment using the Stripe API.
     * @param paymentRequest PaymentRequest DTO containing the payment details.
     * @return PaymentResponse DTO with details of the Stripe charge.
     * @throws StripeException if the payment fails.
     */
    public PaymentResponse charge(PaymentRequest paymentRequest) throws StripeException {
        System.out.println("Initiating payment for: " + paymentRequest);
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentRequest.getAmount());
        chargeParams.put("currency", paymentRequest.getCurrency());
        chargeParams.put("source", paymentRequest.getToken());
        chargeParams.put("description", paymentRequest.getDescription());

        // Execute the charge using Stripe API
        Charge charge = Charge.create(chargeParams);

        // Log the Stripe charge details
        System.out.println("Stripe charge created: " + charge);

        // Map the Stripe Charge object to a PaymentResponse DTO
        return new PaymentResponse(
                charge.getId(),
                charge.getStatus(),
                charge.getAmount(),
                charge.getCurrency()
        );
    }
}