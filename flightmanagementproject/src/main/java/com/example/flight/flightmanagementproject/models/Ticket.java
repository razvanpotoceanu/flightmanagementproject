package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @Positive(message = "Prețul trebuie să fie pozitiv")
    private double price;

    @NotBlank(message = "Numărul locului este obligatoriu")
    private String seatNumber;

    // Relație: Un bilet aparține unui singur pasager
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    // Relație: Un bilet este pentru un singur zbor
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    public Ticket() {}

    public Ticket(double price, String seatNumber, Passenger passenger, Flight flight) {
        this.price = price;
        this.seatNumber = seatNumber;
        this.passenger = passenger;
        this.flight = flight;
    }

    // Getters și Setters
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }
    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }
}