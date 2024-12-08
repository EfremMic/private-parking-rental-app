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

    @NotNull
    private LocalDate availableStartDate;

    @NotNull
    private LocalDate availableEndDate;

    @Size(max = 500)
    private String description;

    @Embedded
    private Location location;

    @NotNull
    private Long userId;

    @NotNull
    private String publisherName;

   @NotNull
   private String publisherEmail;
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        @NotNull
        private String addressName;

        private String gateNumber;
        private String postBoxNumber;

        @NotNull
        private String city;
    }
}
