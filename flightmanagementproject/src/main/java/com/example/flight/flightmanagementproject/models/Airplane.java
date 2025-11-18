package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airplanes")
public class Airplane extends BaseEntity {

    @NotBlank(message = "Modelul avionului este obligatoriu")
    private String modelName;

    @Min(value = 10, message = "Capacitatea trebuie să fie cel puțin 10 locuri")
    private int capacity;

    // Relație: Un avion poate efectua mai multe zboruri
    @OneToMany(mappedBy = "airplane")
    private List<Flight> flights = new ArrayList<>();

    public Airplane() {}

    public Airplane(String modelName, int capacity) {
        this.modelName = modelName;
        this.capacity = capacity;
    }

    // Getters și Setters
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }
}