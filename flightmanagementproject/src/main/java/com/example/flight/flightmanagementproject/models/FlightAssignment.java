package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "flight_assignments")
public class FlightAssignment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    // RELAȚIE 1: Poate fi un angajat al companiei (Pilot, Steward)
    @ManyToOne
    @JoinColumn(name = "airline_employee_id") // Poate fi null
    private AirlineEmployee airlineEmployee;

    // RELAȚIE 2: Poate fi un angajat al aeroportului (Securitate)
    @ManyToOne
    @JoinColumn(name = "airport_employee_id") // Poate fi null
    private AirportEmployee airportEmployee;

    public FlightAssignment() {}

    // Constructor pentru Airline Employee
    public FlightAssignment(Flight flight, AirlineEmployee airlineEmployee) {
        this.flight = flight;
        this.airlineEmployee = airlineEmployee;
    }

    // Constructor pentru Airport Employee
    public FlightAssignment(Flight flight, AirportEmployee airportEmployee) {
        this.flight = flight;
        this.airportEmployee = airportEmployee;
    }

    // Getters și Setters
    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public AirlineEmployee getAirlineEmployee() { return airlineEmployee; }
    public void setAirlineEmployee(AirlineEmployee airlineEmployee) { this.airlineEmployee = airlineEmployee; }

    public AirportEmployee getAirportEmployee() { return airportEmployee; }
    public void setAirportEmployee(AirportEmployee airportEmployee) { this.airportEmployee = airportEmployee; }

    // Metodă ajutătoare pentru a afișa numele angajatului indiferent de tip
    public String getEmployeeName() {
        if (airlineEmployee != null) return airlineEmployee.getName() + " (Airline)";
        if (airportEmployee != null) return airportEmployee.getName() + " (Airport)";
        return "Nespecificat";
    }
}