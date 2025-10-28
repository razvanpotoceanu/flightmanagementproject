package com.example.flight.flightmanagementproject.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

// Adăugăm adnotarea pentru polimorfism, exact ca în modelul "Device"
// Acest lucru ajută la serializarea/deserializarea corectă în JSON
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public class Staff extends BaseEntity { // Extinde BaseEntity
    private String name;

    // Constructor gol necesar pentru Jackson/Spring
    public Staff() {
        super();
    }

    public Staff(String id, String name) {
        super(id); // Apelează constructorul clasei părinte
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}