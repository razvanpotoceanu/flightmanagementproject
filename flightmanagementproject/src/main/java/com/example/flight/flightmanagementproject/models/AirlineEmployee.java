package com.example.flight.flightmanagementproject.models;

import com.example.flight.flightmanagementproject.enums.AirlineEmployeeRole;
import java.util.ArrayList;
import java.util.List;

public class AirlineEmployee extends BaseEntity {
    private String name;
    private AirlineEmployeeRole role; // Enum
    private List<FlightAssignment> assignments;

    public AirlineEmployee() {
        this.assignments = new ArrayList<>();
    }

    public AirlineEmployee(String id, String name, AirlineEmployeeRole role) {
        super(id);
        this.name = name;
        this.role = role;
        this.assignments = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public AirlineEmployeeRole getRole() { return role; }
    public void setRole(AirlineEmployeeRole role) { this.role = role; }
    public List<FlightAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<FlightAssignment> assignments) { this.assignments = assignments; }
}