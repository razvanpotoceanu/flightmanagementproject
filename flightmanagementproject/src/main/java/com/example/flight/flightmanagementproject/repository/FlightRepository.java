package com.example.flight.flightmanagementproject.repository;

import com.example.flight.flightmanagementproject.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class FlightRepository {

    private final Map<String, Flight> flightStore = new HashMap<>();

    public Flight save(Flight flight) {
        flightStore.put(flight.getId(), flight);
        return flight;
    }

    public List<Flight> findAll() {
        return new ArrayList<>(flightStore.values());
    }

    public Optional<Flight> findById(String id) {
        return Optional.ofNullable(flightStore.get(id));
    }

    public void delete(String id) {
        flightStore.remove(id);
    }
}