package com.example.flight.flightmanagementproject.model;

public class Luggage {
    private String id;
    private String ticketId;
    private LuggageStatus status; // Folosim Enum în loc de String

    // Constructor
    public Luggage(String id, String ticketId, LuggageStatus status) {
        this.id = id;
        this.ticketId = ticketId;
        this.status = status;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public LuggageStatus getStatus() {
        return status;
    }

    public void setStatus(LuggageStatus status) {
        this.status = status;
    }
}