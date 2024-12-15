package com.parking.payment.paymentservice.service;

import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.model.Payment;
import com.parking.payment.paymentservice.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private final PaymentRepository paymentRepository; // PaymentRepository dependency
    private final RabbitTemplate rabbitTemplate; // RabbitTemplate dependency

    // Updated constructor to accept RabbitTemplate and PaymentRepository
    public PaymentService(RabbitTemplate rabbitTemplate, PaymentRepository paymentRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.paymentRepository = paymentRepository; // Inject PaymentRepository
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
        logger.info("Stripe API initialized");
    }

    /**
     * Create a PaymentIntent and return the client secret.
     * This is used by the frontend to authenticate the payment with Stripe.
     */
    public Map<String, String> createPaymentIntent(PaymentRequest paymentRequest) {
        try {
            // Create a PaymentIntent using Stripe
            PaymentIntent paymentIntent = PaymentIntent.create(Map.of(
                    "amount", paymentRequest.getAmount(),
                    "currency", paymentRequest.getCurrency(),
                    "metadata", Map.of(
                            "userId", String.valueOf(paymentRequest.getUserId()),
                            "parkingSpotId", String.valueOf(paymentRequest.getParkingSpotId())
                    )
            ));

            // Return clientSecret to the client
            return Map.of("clientSecret", paymentIntent.getClientSecret());
        } catch (Exception e) {
            logger.error("Error creating PaymentIntent: {}", e.getMessage());
            throw new RuntimeException("PaymentIntent creation failed: " + e.getMessage());
        }
    }

    /**
     * Charge a payment using Stripe.
     */
    public PaymentResponse charge(PaymentRequest paymentRequest) throws StripeException {
        logger.info("Processing payment request: {}", paymentRequest);
        Map<String, Object> chargeParams = createChargeParams(paymentRequest);

        Charge charge = Charge.create(chargeParams);
        logger.info("Payment processed successfully. Charge ID: {}, Status: {}", charge.getId(), charge.getStatus());

        // Save the payment record in the database
        Payment payment = new Payment(
                paymentRequest.getUserId(),           // User ID
                paymentRequest.getParkingSpotId(),    // Parking Spot ID
                charge.getId(),                       // Stripe Charge ID
                charge.getAmount(),                   // Amount in cents
                charge.getCurrency(),                 // Currency (e.g., "nok")
                charge.getStatus(),                   // Payment status (e.g., "succeeded")
                new java.util.Date()                  // Current timestamp
        );

        paymentRepository.save(payment); // Save payment in the DB

        return new PaymentResponse(
                charge.getId(),
                charge.getStatus(),
                charge.getAmount(),
                charge.getCurrency()
        );
    }

    /**
     * Create Stripe charge parameters.
     */
    private Map<String, Object> createChargeParams(PaymentRequest paymentRequest) {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentRequest.getAmount());
        chargeParams.put("currency", paymentRequest.getCurrency());
        chargeParams.put("source", paymentRequest.getToken());
        chargeParams.put("description", "Payment for Parking Spot ID: " + paymentRequest.getParkingSpotId());
        return chargeParams;
    }

    /**
     * Retrieve all payments for a given userId.
     *
     * @param userId The ID of the user to fetch payments for.
     * @return List of Payment objects.
     */
    public List<Payment> getPaymentsByUserId(Long userId) {
        if (userId != null) {
            logger.info("Fetching payments for userId: {}", userId);
            return paymentRepository.findByUserId(userId); // Calls PaymentRepository method
        } else {
            logger.info("Fetching all payments (no userId filter applied)");
            return paymentRepository.findAll(); // Get all payments if no userId is provided
        }
    }
}
