package com.parking.payment.paymentservice.listener;

import org.springframework.stereotype.Component;

import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {
    private final PaymentService paymentService;

    @Autowired
    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Listens to payment request messages from RabbitMQ and processes them.
     *
     * @param paymentRequest PaymentRequest DTO containing the payment details.
     */
    @RabbitListener(queues = "${payment.request.queue.name}")
    public void processPayment(PaymentRequest paymentRequest) {
        System.out.println("Received payment request: " + paymentRequest);

        try {
            // Process the payment using the PaymentService
            PaymentResponse paymentResponse = paymentService.charge(paymentRequest);

            // Log success
            System.out.println("Payment successfully processed: " + paymentResponse);

        } catch (StripeException e) {
            // Log the error in case of a failure
            System.err.println("Payment processing failed: " + e.getMessage());
        }
    }
}

