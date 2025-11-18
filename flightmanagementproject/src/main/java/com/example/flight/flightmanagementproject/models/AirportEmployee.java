package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "airport_employees") // Tabel separat în MySQL
public class AirportEmployee extends BaseEntity { // Moștenește direct BaseEntity

    @NotBlank(message = "Numele este obligatoriu")
    private String name;

    @NotBlank(message = "Funcția (Designation) este obligatorie")
    private String designation;

    @NotBlank(message = "Departamentul este obligatoriu")
    private String department;

    public AirportEmployee() {}

    public AirportEmployee(String name, String designation, String department) {
        this.name = name;
        this.designation = designation;
        this.department = department;
    }

    // Getters și Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    // Metoda helper pentru afișare (opțional)
    public String getEmployeeTypeDisplay() {
        return "Airport Employee (" + designation + ")";
    }
}