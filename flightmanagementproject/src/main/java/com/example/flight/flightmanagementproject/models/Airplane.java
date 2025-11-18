package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airplanes")
public class Airplane extends BaseEntity {

    @NotBlank(message = "Modelul este obligatoriu")
    private String modelName;

    // Am redenumit 'number' în 'capacity' pentru claritate.
    // Dacă vrei neapărat 'number', schimbă aici și în HTML.
    @Min(value = 1, message = "Capacitatea trebuie să fie pozitivă")
    private int capacity;

    @OneToMany(mappedBy = "airplane")
    private List<Flight> flights = new ArrayList<>();

    public Airplane() {}

    public Airplane(String modelName, int capacity) {
        this.modelName = modelName;
        this.capacity = capacity;
    }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }
}