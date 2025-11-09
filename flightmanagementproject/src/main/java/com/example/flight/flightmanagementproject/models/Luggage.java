package com.example.flight.flightmanagementproject.models;

// SCHIMBARE AICI: importăm din 'enums'
import com.example.flight.flightmanagementproject.enums.LuggageStatus;

public class Luggage extends BaseEntity {
    private String ticketId;
    private LuggageStatus status;

    public Luggage() {}

    public Luggage(String id, String ticketId, LuggageStatus status) {
        super(id);
        this.ticketId = ticketId;
        this.status = status;
    }

    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }
    public LuggageStatus getStatus() { return status; }
    public void setStatus(LuggageStatus status) { this.status = status; }
}