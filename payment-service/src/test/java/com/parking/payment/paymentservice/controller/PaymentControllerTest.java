package com.parking.payment.paymentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.dto.PaymentResponse;
import com.parking.payment.paymentservice.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false) // Disables CSRF filters for tests
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testChargeCard() throws Exception {
        // Arrange
        PaymentRequest request = new PaymentRequest(
                "tok_visa",       // Token
                1000L,            // Amount as Long
                "usd",            // Currency
                "Test payment",   // Description
                1L,               // userId as Long
                1L                // parkingSpotId as Long
        );

        PaymentResponse mockResponse = new PaymentResponse("ch_1ABC", "succeeded", 1000L, "usd");

        // Mock the behavior of the PaymentService
        when(paymentService.charge(any(PaymentRequest.class))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/payment/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("ch_1ABC"))
                .andExpect(jsonPath("$.status").value("succeeded"))
                .andExpect(jsonPath("$.amount").value(1000L))
                .andExpect(jsonPath("$.currency").value("usd"));
    }
}
