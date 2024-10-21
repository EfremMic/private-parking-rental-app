
# Payment Service Test

This project contains unit and integration tests for the `PaymentController` and `PaymentService` in the Parking Payment Service. The service is responsible for handling credit card payments using Stripe. The tests mock Stripe's API and simulate different payment scenarios such as successful and failed payments.

## Prerequisites

Before running the tests, ensure that you have the following dependencies installed in your project:

- JUnit 5
- Mockito
- Spring Boot Test
- MockMvc for controller tests
- Stripe Java SDK

## Project Structure

```
src
 └── test
     └── java
         └── com.parking.payment.paymentservice
             ├── controller
             │   └── PaymentControllerTest.java
             └── service
                 └── PaymentServiceTest.java
```

### 1. `PaymentControllerTest.java`

This test class focuses on testing the `PaymentController`, specifically the `/charge` endpoint. The test uses `MockMvc` to simulate HTTP requests and verify that the payment service processes them correctly.

#### Key Features:
- **Mocking the Service**: The `PaymentService` is mocked to return a pre-defined response for successful charges.
- **Testing Payment Endpoint**: Tests the `/api/payments/charge` POST endpoint with a valid payment request.

##### Example Test:

```java
@Test
public void testChargeCard() throws Exception {
    // Arrange
    PaymentRequest request = new PaymentRequest("tok_visa", 1000, "usd", "Test payment");
    PaymentResponse mockResponse = new PaymentResponse("ch_1ABC", "succeeded", 1000L, "usd");

    when(paymentService.charge(any(PaymentRequest.class))).thenReturn(mockResponse);

    // Act & Assert
    mockMvc.perform(post("/api/payments/charge")
                    .contentType("application/json")
                    .content("{"token":"tok_visa", "amount":1000, "currency":"usd", "description":"Test payment"}"))
            .andExpect(status().isOk());
}
```

### 2. `PaymentServiceTest.java`

This test class covers the logic inside the `PaymentService`, ensuring that the service correctly handles the payment processing using Stripe's API.

#### Key Features:
- **Mocking Stripe API**: Mocks Stripe's `Charge.create()` method to simulate success and failure scenarios.
- **ReflectionTestUtils**: Injects a test Stripe API key for testing purposes.
- **Testing Payment Logic**: Ensures that the payment request is processed correctly, and the appropriate response is returned.

##### Example Tests:

- **Test Successful Payment**:
```java
@Test
void testChargeSuccess() throws Exception {
    PaymentRequest paymentRequest = new PaymentRequest(TEST_TOKEN, TEST_AMOUNT, TEST_CURRENCY, TEST_DESCRIPTION);

    Charge mockCharge = mock(Charge.class);
    when(mockCharge.getId()).thenReturn(CHARGE_ID);
    when(mockCharge.getStatus()).thenReturn(CHARGE_STATUS);
    when(mockCharge.getAmount()).thenReturn(TEST_AMOUNT);
    when(mockCharge.getCurrency()).thenReturn(TEST_CURRENCY);

    try (MockedStatic<Charge> mockedStatic = mockStatic(Charge.class)) {
        mockedStatic.when(() -> Charge.create(any(Map.class))).thenReturn(mockCharge);

        PaymentResponse paymentResponse = paymentService.charge(paymentRequest);

        assertEquals(CHARGE_ID, paymentResponse.getChargeId());
        assertEquals(CHARGE_STATUS, paymentResponse.getStatus());
        assertEquals(TEST_AMOUNT, paymentResponse.getAmount());
        assertEquals(TEST_CURRENCY, paymentResponse.getCurrency());

        mockedStatic.verify(() -> Charge.create(any(Map.class)), times(1));
    }
}
```

- **Test Payment Failure**:
```java
@Test
void testChargeFailure() throws Exception {
    PaymentRequest paymentRequest = new PaymentRequest(TEST_TOKEN, TEST_AMOUNT, TEST_CURRENCY, TEST_DESCRIPTION);

    try (MockedStatic<Charge> mockedStatic = mockStatic(Charge.class)) {
        mockedStatic.when(() -> Charge.create(any(Map.class))).thenThrow(
            new InvalidRequestException("Charge failed", null, null, null, 400, null)
        );

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            paymentService.charge(paymentRequest);
        });

        assertEquals("Charge failed", exception.getMessage());

        mockedStatic.verify(() -> Charge.create(any(Map.class)), times(1));
    }
}
```

## Running the Tests

You can run the tests using your preferred IDE or through the command line with:

```
./mvnw test
```

## Conclusion

This test suite ensures that the `PaymentService` and `PaymentController` are functioning correctly, handling both success and failure cases when interacting with the Stripe API. It uses unit tests for core logic and integration tests for the controller layer.
