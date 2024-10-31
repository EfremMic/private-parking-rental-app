package com.parking.spotlisting.parkingservice.controller;

import com.parking.spotlisting.parkingservice.dto.ParkingSpotRequest;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import com.parking.spotlisting.parkingservice.service.ParkingSpotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping("/add")
    public ParkingSpot addParkingSpot(@RequestBody ParkingSpotRequest parkingSpotRequest) {
        return parkingSpotService.addParkingSpot(parkingSpotRequest);
    }

    @GetMapping("/list")
    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotService.getAllParkingSpots();
    }
}