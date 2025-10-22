package com.example.flight.flightmanagementproject.model;

public class FlightAssignment {
    private String id;
    private String flightId;
    private String staffId;

    // Constructor
    public FlightAssignment(String id, String flightId, String staffId) {
        this.id = id;
        this.flightId = flightId;
        this.staffId = staffId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}