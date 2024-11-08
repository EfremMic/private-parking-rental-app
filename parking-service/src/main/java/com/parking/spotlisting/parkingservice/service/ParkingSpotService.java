package com.parking.spotlisting.parkingservice.service;

import com.parking.spotlisting.parkingservice.dto.ParkingSpotRequest;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import com.parking.spotlisting.parkingservice.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;

    @Autowired
    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public ParkingSpot addParkingSpot(ParkingSpotRequest parkingSpotRequest) {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setName(parkingSpotRequest.getName());
        parkingSpot.setLocation(parkingSpotRequest.getLocation());
        parkingSpot.setPrice(parkingSpotRequest.getPrice());

        return parkingSpotRepository.save(parkingSpot);
    }


    public void handleUserCreatedEvent(Map<String, Object> event) {
        String eventType = (String) event.get("eventType");

        if ("USER_CREATED".equals(eventType)) {
            String userId = (String) event.get("userId");
            String userEmail = (String) event.get("userEmail");

            System.out.println("Received UserCreatedEvent: userId=" + userId + ", userEmail=" + userEmail);
            // Additional handling can be added here if needed.
        }
    }


    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotRepository.findAll();
    }
}
