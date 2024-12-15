package com.parking.payment.paymentservice.service;

import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.repository.PaymentRepository;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    private static final String TEST_STRIPE_API_KEY = "testApiKey";
    private static final String TEST_TOKEN = "testToken";
    private static final String TEST_DESCRIPTION = "Test Payment";
    private static final Long TEST_AMOUNT = 2000L;
    private static final String TEST_CURRENCY = "usd";
    private static final Long TEST_USER_ID = 1L;
    private static final Long TEST_PARKING_SPOT_ID = 1L;
    private static final String CHARGE_ID = "chargeId";
    private static final String CHARGE_STATUS = "succeeded";

    private PaymentService paymentService;
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        paymentService = new PaymentService(rabbitTemplate, paymentRepository);

        // Inject the Stripe API key
        ReflectionTestUtils.setField(paymentService, "stripeApiKey", TEST_STRIPE_API_KEY);
    }

    @Test
    void testChargeSuccess() throws Exception {
        // Arrange
        PaymentRequest paymentRequest = new PaymentRequest(
                TEST_TOKEN,
                TEST_AMOUNT,
                TEST_CURRENCY,
                TEST_DESCRIPTION,
                TEST_USER_ID,
                TEST_PARKING_SPOT_ID
        );

        Charge mockCharge = mock(Charge.class);
        when(mockCharge.getId()).thenReturn(CHARGE_ID);
        when(mockCharge.getStatus()).thenReturn(CHARGE_STATUS);
        when(mockCharge.getAmount()).thenReturn(TEST_AMOUNT);
        when(mockCharge.getCurrency()).thenReturn(TEST_CURRENCY);

        // Mock the static method Charge.create()
        try (MockedStatic<Charge> mockedStatic = mockStatic(Charge.class)) {
            mockedStatic.when(() -> Charge.create(any(Map.class))).thenReturn(mockCharge);

            // Act
            PaymentResponse paymentResponse = paymentService.charge(paymentRequest);

            // Assert
            assertEquals(CHARGE_ID, paymentResponse.getChargeId());
            assertEquals(CHARGE_STATUS, paymentResponse.getStatus());
            assertEquals(TEST_AMOUNT, paymentResponse.getAmount());
            assertEquals(TEST_CURRENCY, paymentResponse.getCurrency());

            // Verify that Charge.create() was called once
            mockedStatic.verify(() -> Charge.create(any(Map.class)), times(1));
        }
    }

    @Test
    void testChargeFailure() throws Exception {
        // Arrange
        PaymentRequest paymentRequest = new PaymentRequest(
                TEST_TOKEN,
                TEST_AMOUNT,
                TEST_CURRENCY,
                TEST_DESCRIPTION,
                TEST_USER_ID,
                TEST_PARKING_SPOT_ID
        );

        // Simulate an InvalidRequestException during charge creation
        try (MockedStatic<Charge> mockedStatic = mockStatic(Charge.class)) {
            mockedStatic.when(() -> Charge.create(any(Map.class))).thenThrow(
                    new InvalidRequestException("Charge failed", null, null, null, 400, null)
            );

            // Act & Assert
            InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
                paymentService.charge(paymentRequest);
            });

            assertEquals("Charge failed", exception.getMessage());

            // Verify that Charge.create() was called once
            mockedStatic.verify(() -> Charge.create(any(Map.class)), times(1));
        }
    }
}