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
        // Arrange: Set up request and mocked response
        PaymentRequest request = new PaymentRequest(
                "tok_visa12345",  // Updated token to meet @Size constraint (min 10 characters)
                1000L,            // Amount in cents
                "usd",            // Currency
                "Test payment",   // Description
                1L,               // User ID
                1L                // Parking Spot ID
        );

        PaymentResponse mockResponse = new PaymentResponse(
                "ch_1ABC", "succeeded", 1000L, "usd"
        );

        // Mock the behavior of the PaymentService
        when(paymentService.charge(any(PaymentRequest.class))).thenReturn(mockResponse);

        // Act & Assert: Perform the POST request and validate response
        mockMvc.perform(post("/api/payments/charge") // Corrected endpoint path
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // Serialize request to JSON
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.chargeId").value("ch_1ABC")) // Match chargeId field
                .andExpect(jsonPath("$.status").value("succeeded")) // Match status
                .andExpect(jsonPath("$.amount").value(1000L)) // Match amount
                .andExpect(jsonPath("$.currency").value("usd")); // Match currency
    }
}
