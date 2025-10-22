package com.example.flight.flightmanagementproject.model;

import java.util.List;

public class Flight {
    private String id;
    private String name;
    private String noticeBoardId;
    private String airplaneId;
    private List<Ticket> tickets;
    private List<FlightAssignment> flightAssignments;

    // MODIFICĂRI (Pct. 5)
    private String departureAirportCode;
    private String arrivalAirportCode;

    // Constructor
    public Flight(String id, String name, String noticeBoardId, String airplaneId, List<Ticket> tickets, List<FlightAssignment> flightAssignments, String departureAirportCode, String arrivalAirportCode) {
        this.id = id;
        this.name = name;
        this.noticeBoardId = noticeBoardId;
        this.airplaneId = airplaneId;
        this.tickets = tickets;
        this.flightAssignments = flightAssignments;
        this.departureAirportCode = departureAirportCode;
        this.arrivalAirportCode = arrivalAirportCode;
    }

    // Getters and Setters (pentru toate câmpurile)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoticeBoardId() {
        return noticeBoardId;
    }

    public void setNoticeBoardId(String noticeBoardId) {
        this.noticeBoardId = noticeBoardId;
    }

    public String getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(String airplaneId) {
        this.airplaneId = airplaneId;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<FlightAssignment> getFlightAssignments() {
        return flightAssignments;
    }

    public void setFlightAssignments(List<FlightAssignment> flightAssignments) {
        this.flightAssignments = flightAssignments;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }
}