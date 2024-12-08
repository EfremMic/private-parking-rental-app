package com.parking.spotlisting.parkingservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {
    @NotNull(message = "Parking spot ID is required")
    private Long parkingSpotId;

    @NotNull(message = "Message content is required")
    private String message;
}
