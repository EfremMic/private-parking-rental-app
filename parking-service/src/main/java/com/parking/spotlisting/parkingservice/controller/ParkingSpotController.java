package com.parking.spotlisting.parkingservice.controller;

import com.parking.spotlisting.parkingservice.dto.ParkingSpotRequest;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import com.parking.spotlisting.parkingservice.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    // Add a parking spot
    @PostMapping("/add")
    public ResponseEntity<?> addParkingSpot(@RequestBody @Valid ParkingSpotRequest parkingSpotRequest) {
        try {
            ParkingSpot newSpot = parkingSpotService.addParkingSpot(parkingSpotRequest);
            return ResponseEntity.ok(newSpot);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("message", "Failed to add parking spot", "error", e.getMessage()));
        }
    }

    // Get a parking spot by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> getParkingSpotById(@PathVariable Long id) {
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(id);
        if (parkingSpot == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(parkingSpot);
    }

    // Get all parking spots
    @GetMapping("/list")
    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotService.getAllParkingSpots();
    }

    // Get parking spots by user
    @GetMapping("/user/{userId}/list")
    public List<ParkingSpot> getParkingSpotsByUserId(@PathVariable Long userId) {
        return parkingSpotService.getParkingSpotsByUserId(userId);
    }
}
