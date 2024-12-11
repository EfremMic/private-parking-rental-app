package com.parking.payment.paymentservice.dto;

public class PaymentResult {
    private Long userId;
    private Long parkingSpotId;
    private boolean success;
    private String chargeId;
    private String errorMessage; // Optional for failed payments

    // Constructor
    public PaymentResult(Long userId, Long parkingSpotId, boolean success, String chargeId, String errorMessage) {
        this.userId = userId;
        this.parkingSpotId = parkingSpotId;
        this.success = success;
        this.chargeId = chargeId;
        this.errorMessage = errorMessage;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
