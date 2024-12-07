package com.parking.spotlisting.parkingservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ParkingSpotRequest {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Region is required")
    private String region;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Available start date is required")
    private LocalDate availableStartDate;

    @NotNull(message = "Available end date is required")
    private LocalDate availableEndDate;

    private String description; // Optional field

    @NotNull(message = "Location is required")
    private Location location;

    @NotNull(message = "User ID is required")
    private Long userId; // Publisher's User ID

    @Data
    public static class Location {
        @NotNull(message = "Address name is required")
        private String addressName;

        private String gateNumber; // Optional field

        private String postBoxNumber; // Optional field

        @NotNull(message = "City is required")
        private String city;
    }
}
