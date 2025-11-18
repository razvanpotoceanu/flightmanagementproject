package com.example.flight.flightmanagementproject.models;

import com.example.flight.flightmanagementproject.enums.AirlineEmployeeRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "airline_employees") // Tabel separat în MySQL
public class AirlineEmployee extends BaseEntity { // Moștenește direct BaseEntity

    @NotBlank(message = "Numele este obligatoriu")
    private String name;

    @NotNull(message = "Rolul este obligatoriu")
    @Enumerated(EnumType.STRING) // Salvăm numele enum-ului (PILOT, etc.)
    private AirlineEmployeeRole role;

    // Opțional: Relații cu alte tabele (dacă există)
    // @OneToMany...

    public AirlineEmployee() {}

    public AirlineEmployee(String name, AirlineEmployeeRole role) {
        this.name = name;
        this.role = role;
    }

    // Getters și Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public AirlineEmployeeRole getRole() { return role; }
    public void setRole(AirlineEmployeeRole role) { this.role = role; }

    // Metoda helper pentru afișare (opțional)
    public String getEmployeeTypeDisplay() {
        return "Airline Employee (" + (role != null ? role.name() : "-") + ")";
    }
}