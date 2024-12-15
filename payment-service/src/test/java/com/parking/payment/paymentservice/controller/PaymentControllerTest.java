package com.parking.payment.paymentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.payment.paymentservice.dto.PaymentRequest;
import com.parking.payment.paymentservice.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable CSRF filters for testing
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatePaymentIntent_Success() throws Exception {
        // Arrange: Input for the API request
        PaymentRequest paymentRequest = new PaymentRequest(
                "tok_visa", // Stripe token
                 1000L,       // Amount in cents
                "nok",       // Currency
                "Payment for parking spot", // Description
                1L,          // User ID
                2L           // Parking Spot ID
        );

        // Mocked response from PaymentService
        Map<String, String> mockResponse = Map.of("clientSecret", "cs_test_1234567890abcdef");

        // Define behavior for mocked service
        when(paymentService.createPaymentIntent(any(PaymentRequest.class))).thenReturn(mockResponse);

        // Act: Perform POST request to test the endpoint
        mockMvc.perform(post("/api/payment/intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest))) // Convert request to JSON
                .andExpect(status().isOk()) // Assert HTTP 200 OK
                .andExpect(jsonPath("$.clientSecret").value("cs_test_1234567890abcdef")); // Assert clientSecret in response
    }

    @Test
    public void testCreatePaymentIntent_Error() throws Exception {
        // Arrange: Input request
        PaymentRequest paymentRequest = new PaymentRequest(
                "tok_visa",
                1000L,
                "nok",
                "Payment for parking spot",
                1L,
                2L
        );

        // Simulate failure scenario
        when(paymentService.createPaymentIntent(any(PaymentRequest.class)))
                .thenThrow(new RuntimeException("PaymentIntent creation failed"));

        // Act & Assert: Perform POST request and expect 500 status with error message
        mockMvc.perform(post("/api/payment/intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("PaymentIntent creation failed")); // Make sure it matches
    }
}
