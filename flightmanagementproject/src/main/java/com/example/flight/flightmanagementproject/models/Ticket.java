package com.example.flight.flightmanagementproject.models;

import java.util.List;
import java.util.ArrayList;

public class Ticket extends BaseEntity { // Extinde BaseEntity
    private String passengerId;
    private String flightId;
    private double price;
    private String seatNumber;
    private List<Luggage> luggages;

    public Ticket() {
        this.luggages = new ArrayList<>();
    } // Constructor gol

    public Ticket(String id, String passengerId, String flightId, double price, String seatNumber, List<Luggage> luggages) {
        super(id); // Apel părinte
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.price = price;
        this.seatNumber = seatNumber;
        this.luggages = (luggages != null) ? luggages : new ArrayList<>();
    }

    //... Toți Getter-ii și Setter-ii (fără getId/setId) ...
    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }
    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public List<Luggage> getLuggages() { return luggages; }
    public void setLuggages(List<Luggage> luggages) { this.luggages = luggages; }
}