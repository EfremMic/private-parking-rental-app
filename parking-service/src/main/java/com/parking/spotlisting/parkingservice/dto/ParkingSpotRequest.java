package com.parking.spotlisting.parkingservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ParkingSpotRequest {
    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Region is required")
    private String region;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Available start date is required")
    private LocalDate availableStartDate;

    @NotNull(message = "Available end date is required")
    private LocalDate availableEndDate;

    private String description;

    @NotNull(message = "Location is required")
    private Location location;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Publisher Name is required")
    private String publisherName;

    @NotNull(message = "Publisher Email is required")
    private String publisherEmail;

    @Data
    public static class Location {
        @NotNull(message = "Address name is required")
        private String addressName;

        private String gateNumber;
        private String postBoxNumber;

        @NotNull(message = "City is required")
        private String city;
    }
}
