package com.example.flight.flightmanagementproject.models;

import java.util.List;
import java.util.ArrayList;

public class Passenger extends BaseEntity { // Extinde BaseEntity
    private String name;
    private String currency;
    private List<Ticket> tickets;
    private String email;

    public Passenger() {
        this.tickets = new ArrayList<>();
    } // Constructor gol

    public Passenger(String id, String name, String currency, List<Ticket> tickets, String email) {
        super(id); // Apel părinte
        this.name = name;
        this.currency = currency;
        this.tickets = (tickets != null) ? tickets : new ArrayList<>();
        this.email = email;
    }

    //... Toți Getter-ii și Setter-ii (fără getId/setId) ...
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}