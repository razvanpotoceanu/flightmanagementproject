package com.example.flight.flightmanagementproject.models;

import java.util.List;
import java.util.ArrayList;

public class Airplane extends BaseEntity { // Extinde BaseEntity
    private int number;
    private List<Flight> flights;
    private String modelName;

    public Airplane() {
        this.flights = new ArrayList<>();
    } // Constructor gol

    public Airplane(String id, int number, List<Flight> flights, String modelName) {
        super(id); // Apel părinte
        this.number = number;
        this.flights = (flights != null) ? flights : new ArrayList<>();
        this.modelName = modelName;
    }

    // Getters and Setters (fără getId/setId, care sunt în BaseEntity)
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
}