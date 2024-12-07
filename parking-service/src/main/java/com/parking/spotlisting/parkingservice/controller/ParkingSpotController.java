package com.parking.spotlisting.parkingservice.controller;

import com.parking.spotlisting.parkingservice.dto.ParkingSpotRequest;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import com.parking.spotlisting.parkingservice.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ParkingSpot> addParkingSpot(@RequestBody @Valid ParkingSpotRequest parkingSpotRequest) {
        try {
            ParkingSpot newSpot = parkingSpotService.addParkingSpot(parkingSpotRequest);
            return ResponseEntity.ok(newSpot);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.badRequest().build();
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

}
