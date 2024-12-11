package com.parking.payment.paymentservice.controller;

import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testChargeCard() throws Exception {
        // Arrange: Update to use 6-argument constructor
        PaymentRequest request = new PaymentRequest(
                "tok_visa",       // Token
                1000L,            // Amount (use long instead of int)
                "usd",            // Currency
                "Test payment",   // Description
                1L,               // userId (use 1L or any other valid user ID)
                1L                // parkingSpotId (use 1L or any other valid parking spot ID)
        );

        PaymentResponse mockResponse = new PaymentResponse("ch_1ABC", "succeeded", 1000L, "usd");

        // When PaymentService is called, return a mock response
        when(paymentService.charge(any(PaymentRequest.class))).thenReturn(mockResponse);

        // Act & Assert: Make sure to include all required properties in the JSON content
        mockMvc.perform(post("/api/payments/charge")
                        .contentType("application/json")
                        .content("{\"token\":\"tok_visa\", \"amount\":1000, \"currency\":\"usd\", \"description\":\"Test payment\", \"userId\":1, \"parkingSpotId\":1}"))
                .andExpect(status().isOk());
    }
}