package com.parking.spotlisting.parkingservice.controller;

/*
import com.parking.spotlisting.parkingservice.dto.ContactRequest;
import com.parking.spotlisting.parkingservice.dto.ParkingSpotRequest;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import com.parking.spotlisting.parkingservice.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> getParkingSpotById(@PathVariable Long id) {
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(id);
        return ResponseEntity.ok(parkingSpot);
    }

    @GetMapping("/list")
    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotService.getAllParkingSpots();
    }

    @GetMapping("/user/{userId}/list")
    public List<ParkingSpot> getParkingSpotsByUserId(@PathVariable Long userId) {
        return parkingSpotService.getParkingSpotsByUserId(userId);
    }

    @GetMapping("/search")
    public List<ParkingSpot> searchParkingSpots(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return parkingSpotService.searchParkingSpots(city, startDate, endDate);
    }

    @GetMapping("/api/parking/{id}")
    public ResponseEntity<ParkingSpot> getParkingSpotById(@PathVariable Long id) {
        ParkingSpot spot = parkingService.findById(id);
        if (spot != null) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(spot);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}
*/


import com.parking.spotlisting.parkingservice.dto.ContactRequest;
import com.parking.spotlisting.parkingservice.dto.ParkingSpotRequest;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import com.parking.spotlisting.parkingservice.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        try {
            ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(id);
            if (parkingSpot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(parkingSpot);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get a list of all parking spots
    @GetMapping("/list")
    public ResponseEntity<List<ParkingSpot>> getAllParkingSpots() {
        List<ParkingSpot> parkingSpots = parkingSpotService.getAllParkingSpots();
        return ResponseEntity.ok(parkingSpots);
    }

    // Get all parking spots for a specific user
    @GetMapping("/user/{userId}/list")
    public ResponseEntity<List<ParkingSpot>> getParkingSpotsByUserId(@PathVariable Long userId) {
        List<ParkingSpot> parkingSpots = parkingSpotService.getParkingSpotsByUserId(userId);
        return ResponseEntity.ok(parkingSpots);
    }

    // Search parking spots by city, start date, and end date
    @GetMapping("/search")
    public ResponseEntity<List<ParkingSpot>> searchParkingSpots(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        List<ParkingSpot> results = parkingSpotService.searchParkingSpots(city, startDate, endDate);
        return ResponseEntity.ok(results);
    }
}
