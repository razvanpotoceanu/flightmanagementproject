package com.example.flight.flightmanagementproject.models;

public class Luggage extends BaseEntity { // Extinde BaseEntity
    private String ticketId;
    private LuggageStatus status;

    public Luggage() {} // Constructor gol

    public Luggage(String id, String ticketId, LuggageStatus status) {
        super(id); // Apel părinte
        this.ticketId = ticketId;
        this.status = status;
    }

    //... Toți Getter-ii și Setter-ii (fără getId/setId) ...
    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }
    public LuggageStatus getStatus() { return status; }
    public void setStatus(LuggageStatus status) { this.status = status; }
}