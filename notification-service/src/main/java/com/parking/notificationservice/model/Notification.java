package com.parking.notificationservice.model;

import jakarta.persistence.*;

@Entity
public class Notification {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_SENT = "SENT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private String recipient; // Email or user ID
    private String type; // E.g., "EMAIL", "SMS"
    private String status; // E.g., "PENDING", "SENT"

    public Notification() {
    }

    public Notification(String title, String message, String recipient, String type, String status) {
        this.title = title;
        this.message = message;
        this.recipient = recipient;
        this.type = type;
        this.status = status;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
