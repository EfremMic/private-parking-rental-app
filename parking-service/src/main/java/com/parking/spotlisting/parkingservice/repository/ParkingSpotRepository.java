package com.parking.spotlisting.parkingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
}