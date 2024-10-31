
package com.parking.spotlisting.parkingservice.dto;

public class ParkingSpotRequest {
    private String name;
    private String location;
    private Double price;
    private Long userId;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}