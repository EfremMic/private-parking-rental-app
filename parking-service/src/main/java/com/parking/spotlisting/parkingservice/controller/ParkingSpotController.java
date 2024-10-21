package com.parking.spotlisting.parkingservice.controller;

import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import com.parking.spotlisting.parkingservice.service.ParkingSpotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @GetMapping("/list")
    public List<ParkingSpot> getParkingSpots() {
        return parkingSpotService.findAllSpots();
    }

    @PostMapping("/add")
    public ParkingSpot addParkingSpot(@RequestBody ParkingSpot spot) {
        return parkingSpotService.addParkingSpot(spot);
    }
}