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
        // Arrange
        PaymentRequest request = new PaymentRequest("tok_visa", 1000, "usd", "Test payment");
        PaymentResponse mockResponse = new PaymentResponse("ch_1ABC", "succeeded", 1000L, "usd");

        when(paymentService.charge(any(PaymentRequest.class))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/payments/charge")
                        .contentType("application/json")
                        .content("{\"token\":\"tok_visa\", \"amount\":1000, \"currency\":\"usd\", \"description\":\"Test payment\"}"))
                .andExpect(status().isOk());
    }
}
