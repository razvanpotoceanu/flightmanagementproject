package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @Positive(message = "Prețul trebuie să fie pozitiv")
    private double price;

    @NotBlank(message = "Locul este obligatoriu")
    private String seatNumber;

    @NotNull(message = "Trebuie să selectați un pasager")
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @NotNull(message = "Trebuie să selectați un zbor")
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    // --- CORECȚIA ESTE AICI ---
    // Adăugăm relația inversă către Bagaje cu CASCADE ALL.
    // Când ștergi un bilet (sau pasagerul lui), se șterg și bagajele.
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Luggage> luggages = new ArrayList<>();

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

    // Getter/Setter pentru lista de bagaje
    public List<Luggage> getLuggages() { return luggages; }
    public void setLuggages(List<Luggage> luggages) { this.luggages = luggages; }
}