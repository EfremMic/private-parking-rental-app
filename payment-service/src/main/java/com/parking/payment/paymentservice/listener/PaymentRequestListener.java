package com.parking.payment.paymentservice.listener;

import com.parking.payment.paymentservice.dto.PaymentResult;
import com.parking.payment.paymentservice.model.PaymentRequestModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PaymentRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestListener.class);
    private final RabbitTemplate rabbitTemplate;

    @Value("${payment.result.queue.name}")
    private String paymentResultQueue;

    public PaymentRequestListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${payment.request.queue.name}")
    public void processPaymentRequest(PaymentRequestModel paymentRequest) {
        logger.info("Received payment request: {}", paymentRequest);
        try {
            // Simulate Stripe payment processing
            boolean paymentSuccess = processPayment(paymentRequest);
            PaymentResult paymentResult = new PaymentResult(
                    paymentRequest.getUserId(),
                    paymentRequest.getParkingSpotId(),
                    paymentSuccess,
                    paymentSuccess ? "Success ID" : null,
                    paymentSuccess ? null : "Payment failed"
            );

            // Publish payment result
            rabbitTemplate.convertAndSend(paymentResultQueue, paymentResult);
            logger.info("Payment result published: {}", paymentResult);
        } catch (Exception e) {
            logger.error("Error processing payment request: {}", e.getMessage());
            PaymentResult paymentResult = new PaymentResult(
                    paymentRequest.getUserId(),
                    paymentRequest.getParkingSpotId(),
                    false,
                    null,
                    "Payment processing error"
            );
            rabbitTemplate.convertAndSend(paymentResultQueue, paymentResult);
            logger.error("Failed payment result published: {}", paymentResult);
        }
    }

    private boolean processPayment(PaymentRequestModel request) {
        logger.info("Processing payment for request: {}", request);
        // Simulate Stripe payment logic here
        return true; // Simulate a successful payment
    }
}
