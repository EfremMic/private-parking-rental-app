package com.parking.payment.paymentservice.service;

import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private final RabbitTemplate rabbitTemplate;

    public PaymentService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
        logger.info("Stripe API initialized");
    }

    public PaymentResponse charge(PaymentRequest paymentRequest) throws StripeException {
        logger.info("Processing payment request: {}", paymentRequest);
        Map<String, Object> chargeParams = createChargeParams(paymentRequest);

        Charge charge = Charge.create(chargeParams);
        logger.info("Payment processed successfully. Charge ID: {}, Status: {}", charge.getId(), charge.getStatus());

        return new PaymentResponse(
                charge.getId(),
                charge.getStatus(),
                charge.getAmount(),
                charge.getCurrency()
        );
    }

    private Map<String, Object> createChargeParams(PaymentRequest paymentRequest) {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentRequest.getAmount());
        chargeParams.put("currency", paymentRequest.getCurrency());
        chargeParams.put("source", paymentRequest.getToken());
        chargeParams.put("description", paymentRequest.getDescription());
        return chargeParams;
    }
}
