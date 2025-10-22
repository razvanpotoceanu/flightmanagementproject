package com.example.flight.flightmanagementproject.repository;

import com.example.flight.flightmanagementproject.model.Airplane;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AirplaneRepository {

    private final Map<String, Airplane> airplaneStore = new HashMap<>();

    public Airplane save(Airplane airplane) {
        airplaneStore.put(airplane.getId(), airplane);
        return airplane;
    }

    public List<Airplane> findAll() {
        return new ArrayList<>(airplaneStore.values());
    }

    public Optional<Airplane> findById(String id) {
        return Optional.ofNullable(airplaneStore.get(id));
    }

    public void delete(String id) {
        airplaneStore.remove(id);
    }
}