package com.example.flight.flightmanagementproject.model;

import java.util.List;

public class Passenger {
    private String id;
    private String name;
    private String currency;
    private List<Ticket> tickets;

    // MODIFICARE (Pct. 5)
    private String email;

    // Constructor
    public Passenger(String id, String name, String currency, List<Ticket> tickets, String email) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.tickets = tickets;
        this.email = email;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}