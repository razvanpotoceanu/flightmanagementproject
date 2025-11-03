package com.example.flight.flightmanagementproject.models;

import java.util.List;
import java.util.ArrayList;

public class AirlineEmployee extends Staff {
    private String role;
    private List<FlightAssignment> assignments;

    public AirlineEmployee() {
        this.assignments = new ArrayList<>();
    } // Constructor gol

    public AirlineEmployee(String id, String name, String role, List<FlightAssignment> assignments) {
        super(id, name);
        this.role = role;
        this.assignments = (assignments != null) ? assignments : new ArrayList<>();
    }

    // Getters and Setters
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<FlightAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<FlightAssignment> assignments) { this.assignments = assignments;

    }

    @Override
    public String getEmployeeType() {
        return this.role;
    }

}