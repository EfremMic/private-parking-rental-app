package com.parking.spotlisting.parkingservice.controller;

import com.parking.spotlisting.parkingservice.dto.ParkingSpotRequest;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import com.parking.spotlisting.parkingservice.service.ParkingSpotService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    // Add a new endpoint to add a parking spot
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED) // Explicitly set 201 status
    public ParkingSpot addParkingSpot(@RequestBody ParkingSpotRequest parkingSpotRequest) {
        ParkingSpot newSpot = parkingSpotService.addParkingSpot(parkingSpotRequest);
        System.out.println("Added new parking spot: " + newSpot);
        return newSpot;
    }

    // Add a new endpoint to fetch all parking spots
    @GetMapping("/list")
    public List<ParkingSpot> getAllParkingSpots() {
        List<ParkingSpot> parkingSpots = parkingSpotService.getAllParkingSpots();
        System.out.println("Fetching all parking spots: " + parkingSpots); // Log fetched spots
        return parkingSpots;
    }


}
