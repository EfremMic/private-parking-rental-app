package com.parking.payment.paymentservice.listener;

import org.springframework.stereotype.Component;
import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PaymentListener {

    private static final Logger logger = LoggerFactory.getLogger(PaymentListener.class);
    private final PaymentService paymentService;

    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Listens for payment requests on the payment request queue.
     *
     * @param paymentRequest PaymentRequest DTO containing the payment details.
     */
    @RabbitListener(queues = "${payment.request.queue.name}")
    public void processPayment(PaymentRequest paymentRequest) {
        logger.info("Received payment request: {}", paymentRequest);

        try {
            // Process the payment using PaymentService
            PaymentResponse paymentResponse = paymentService.charge(paymentRequest);
            logger.info("Payment successfully processed: {}", paymentResponse);

            // Optionally, send the response to a result queue
            // Publish logic here if needed
        } catch (StripeException e) {
            logger.error("Payment processing failed: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred during payment processing: {}", e.getMessage());
        }
    }
}
