package com.parking.spotlisting.parkingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    // Add custom queries here
}