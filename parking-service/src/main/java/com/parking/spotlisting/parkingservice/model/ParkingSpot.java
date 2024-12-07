package com.parking.spotlisting.parkingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "parking_spots", indexes = @Index(columnList = "userId"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotNull
    @Size(min = 3, max = 50, message = "Region must be between 3 and 50 characters")
    private String region;

    @NotNull
    private Double price;

    @NotNull(message = "Start date is required")
    private LocalDate availableStartDate;

    @NotNull(message = "End date is required")
    private LocalDate availableEndDate;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Embedded
    private Location location;

    @NotNull
    private Long userId; // Publisher (user ID)

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        @NotNull
        @Size(min = 3, max = 100, message = "Address name must be between 3 and 100 characters")
        private String addressName;

        private String gateNumber;

        private String postBoxNumber;

        @NotNull
        @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
        private String city;
    }
}
