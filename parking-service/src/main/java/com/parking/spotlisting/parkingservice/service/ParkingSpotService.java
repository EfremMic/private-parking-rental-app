package com.parking.spotlisting.parkingservice.service;

import com.parking.spotlisting.parkingservice.repository.ParkingSpotRepository;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public List<ParkingSpot> findAllSpots() {
        return parkingSpotRepository.findAll();
    }

    public ParkingSpot addParkingSpot(ParkingSpot spot) {
        return parkingSpotRepository.save(spot);
    }
}