package com.example.flight.flightmanagementproject.models;

import java.util.List;
import java.util.ArrayList;

// Asigură-te că folosești BaseEntity
public class Flight extends BaseEntity {

    // Câmpuri care se potrivesc cu formularul HTML
    private String flightNumber;
    private String departureTime; // Vom presupune String, așa cum e în formular
    private String arrivalTime;   // Vom presupune String

    // Câmpurile tale existente (le păstrăm dacă sunt necesare pentru alte logici)
    private String noticeBoardId;
    private String airplaneId;
    private List<Ticket> tickets;
    private List<FlightAssignment> flightAssignments;

    // Constructor gol
    public Flight() {
        this.tickets = new ArrayList<>();
        this.flightAssignments = new ArrayList<>();
    }

    // Constructor complet (poți adăuga/elimina câmpuri după nevoie)
    public Flight(String id, String flightNumber, String departureTime, String arrivalTime) {
        super(id); // Apel corect la BaseEntity
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.tickets = new ArrayList<>();
        this.flightAssignments = new ArrayList<>();
    }

    // --- Getters și Setters pentru câmpurile din formular ---

    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    // --- Getters și Setters pentru câmpurile vechi (dacă mai e nevoie de ele) ---

    public String getNoticeBoardId() { return noticeBoardId; }
    public void setNoticeBoardId(String noticeBoardId) { this.noticeBoardId = noticeBoardId; }
    public String getAirplaneId() { return airplaneId; }
    public void setAirplaneId(String airplaneId) { this.airplaneId = airplaneId; }
    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
    public List<FlightAssignment> getFlightAssignments() { return flightAssignments; }
    public void setFlightAssignments(List<FlightAssignment> flightAssignments) { this.flightAssignments = flightAssignments; }
}