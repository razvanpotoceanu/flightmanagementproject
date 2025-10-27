package com.example.flight.flightmanagementproject.models;

import java.time.LocalDate; // Import corect pentru dată
import java.util.List;

public class NoticeBoard extends BaseEntity {
    private String id;
    private LocalDate date; // CORECȚIE (Pct. 7)
    private List<Flight> flightsOfTheDay;

    // Constructor
    public NoticeBoard(String id, LocalDate date, List<Flight> flightsOfTheDay) {
        this.id = id;
        this.date = date;
        this.flightsOfTheDay = flightsOfTheDay;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Flight> getFlightsOfTheDay() {
        return flightsOfTheDay;
    }

    public void setFlightsOfTheDay(List<Flight> flightsOfTheDay) {
        this.flightsOfTheDay = flightsOfTheDay;
    }
}