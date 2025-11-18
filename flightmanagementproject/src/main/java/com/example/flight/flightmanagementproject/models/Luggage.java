package com.example.flight.flightmanagementproject.models;

import com.example.flight.flightmanagementproject.enums.LuggageStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "luggages")
public class Luggage extends BaseEntity {

    // Relație: Mai multe bagaje pot aparține unui bilet
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Statusul este obligatoriu")
    private LuggageStatus status;

    public Luggage() {}

    public Luggage(Ticket ticket, LuggageStatus status) {
        this.ticket = ticket;
        this.status = status;
    }

    // Getters și Setters
    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public LuggageStatus getStatus() { return status; }
    public void setStatus(LuggageStatus status) { this.status = status; }
}