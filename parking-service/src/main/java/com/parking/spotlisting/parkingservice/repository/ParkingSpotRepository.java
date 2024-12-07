package com.parking.spotlisting.parkingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByUserId(Long userId); // Fetch parking spots by user ID
}