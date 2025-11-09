package com.example.flight.flightmanagementproject.models;

// Asigură-te că acest fișier există și este corect
public class FlightAssignment extends BaseEntity {
    private String flightId;
    private String staffId;

    public FlightAssignment() { super(); }
    public FlightAssignment(String id, String flightId, String staffId) {
        super(id);
        this.flightId = flightId;
        this.staffId = staffId;
    }
    // Getters and Setters
    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
}