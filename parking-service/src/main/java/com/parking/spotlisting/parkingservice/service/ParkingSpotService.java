package com.parking.spotlisting.parkingservice.service;

import com.parking.spotlisting.parkingservice.dto.ParkingSpotRequest;
import com.parking.spotlisting.parkingservice.repository.ParkingSpotRepository;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;

    @Autowired
    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    // Updated method to take ParkingSpotRequest
    public ParkingSpot addParkingSpot(ParkingSpotRequest parkingSpotRequest) {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setName(parkingSpotRequest.getName());
        parkingSpot.setLocation(parkingSpotRequest.getLocation());
        parkingSpot.setPrice(parkingSpotRequest.getPrice());
        parkingSpot.setId(parkingSpotRequest.getUserId());

        return parkingSpotRepository.save(parkingSpot);  // Saves to H2
    }

    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotRepository.findAll();
    }
}