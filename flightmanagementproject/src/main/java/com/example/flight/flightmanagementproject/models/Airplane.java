package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airplanes")
public class Airplane extends BaseEntity {

    @NotBlank(message = "Modelul este obligatoriu")
    private String modelName;

    @NotNull(message = "Capacitatea este obligatorie")
    @Min(value = 10, message = "Capacitatea trebuie să fie un număr de cel puțin 10")
    private Integer capacity; // Folosim Integer pentru a putea valida null

    @OneToMany(mappedBy = "airplane")
    private List<Flight> flights = new ArrayList<>();

    public Airplane() {}

    public Airplane(String modelName, Integer capacity) {
        this.modelName = modelName;
        this.capacity = capacity;
    }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }
}