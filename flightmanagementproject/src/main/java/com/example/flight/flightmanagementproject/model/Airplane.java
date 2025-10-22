package com.example.flight.flightmanagementproject.model;

import java.util.List;

public class Airplane {
    private String id;
    private int number;
    private List<Flight> flights;

    // MODIFICARE (Pct. 5)
    private String modelName; // ex: "Boeing 747"

    // Constructor
    public Airplane(String id, int number, List<Flight> flights, String modelName) {
        this.id = id;
        this.number = number;
        this.flights = flights;
        this.modelName = modelName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}