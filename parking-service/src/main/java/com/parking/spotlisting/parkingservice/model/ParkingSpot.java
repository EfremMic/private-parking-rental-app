package com.parking.spotlisting.parkingservice.model;
import jakarta.persistence.*;



@Entity
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private double price;

    // Getters and Setters
}


