package com.example.flight.flightmanagementproject.model;

import java.util.List;

public class AirlineEmployee extends Staff {
    private String role; // ex: "Pilot"
    private List<FlightAssignment> assignments;

    // Constructor
    public AirlineEmployee(String id, String name, String role, List<FlightAssignment> assignments) {
        super(id, name); // Apelează constructorul clasei părinte
        this.role = role;
        this.assignments = assignments;
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<FlightAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<FlightAssignment> assignments) {
        this.assignments = assignments;
    }
}