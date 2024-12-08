package com.parking.spotlisting.parkingservice.service;

import com.parking.spotlisting.parkingservice.dto.ParkingSpotRequest;
import com.parking.spotlisting.parkingservice.exception.ResourceNotFoundException;
import com.parking.spotlisting.parkingservice.model.ParkingSpot;
import com.parking.spotlisting.parkingservice.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpot addParkingSpot(ParkingSpotRequest parkingSpotRequest) {
        ParkingSpot.Location location = new ParkingSpot.Location(
                parkingSpotRequest.getLocation().getAddressName(),
                parkingSpotRequest.getLocation().getGateNumber(),
                parkingSpotRequest.getLocation().getPostBoxNumber(),
                parkingSpotRequest.getLocation().getCity()
        );

        ParkingSpot parkingSpot = new ParkingSpot(
                null,
                parkingSpotRequest.getName(),
                parkingSpotRequest.getRegion(),
                parkingSpotRequest.getPrice(),
                parkingSpotRequest.getAvailableStartDate(),
                parkingSpotRequest.getAvailableEndDate(),
                parkingSpotRequest.getDescription(),
                location,
                parkingSpotRequest.getUserId(),
                parkingSpotRequest.getPublisherName(),
                parkingSpotRequest.getPublisherEmail() // Add this line
        );

        return parkingSpotRepository.save(parkingSpot);
    }


    public ParkingSpot getParkingSpotById(Long id) {
        return parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking spot not found"));
    }

    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotRepository.findAll();
    }

    public List<ParkingSpot> getParkingSpotsByUserId(Long userId) {
        return parkingSpotRepository.findByUserId(userId);
    }

    public List<ParkingSpot> searchParkingSpots(String city, String startDate, String endDate) {
        LocalDate start = (startDate != null) ? LocalDate.parse(startDate) : null;
        LocalDate end = (endDate != null) ? LocalDate.parse(endDate) : null;

        return parkingSpotRepository.findAll().stream()
                .filter(spot -> city == null || spot.getLocation().getCity().equalsIgnoreCase(city))
                .filter(spot -> start == null || !spot.getAvailableStartDate().isAfter(end))
                .filter(spot -> end == null || !spot.getAvailableEndDate().isBefore(start))
                .collect(Collectors.toList());
    }
}
