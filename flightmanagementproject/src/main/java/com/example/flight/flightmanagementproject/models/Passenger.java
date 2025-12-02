package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "passengers")
public class Passenger extends BaseEntity {

    @NotBlank(message = "Numele este obligatoriu")
    @Size(min = 3, max = 100, message = "Numele trebuie să aibă între 3 și 100 de caractere")
    private String name;

    @NotBlank(message = "Emailul este obligatoriu")
    @Email(message = "Formatul emailului este invalid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Valuta este obligatorie")
    private String currency;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public Passenger() {}

    public Passenger(String name, String email, String currency) {
        this.name = name;
        this.email = email;
        this.currency = currency;
    }

    // Getters și Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
}