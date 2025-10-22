package com.example.flight.flightmanagementproject.model;

public class AirportEmployee extends Staff {
    private String designation;
    private String department;

    // Constructor
    public AirportEmployee(String id, String name, String designation, String department) {
        super(id, name);
        this.designation = designation;
        this.department = department;
    }

    // Getters and Setters
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}