package com.example.flight.flightmanagementproject.repository;

import com.example.flight.flightmanagementproject.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PassengerRepository {

    // Stocare In-Memory
    private final Map<String, Passenger> passengerStore = new HashMap<>();

    public Passenger save(Passenger passenger) {
        if (passenger.getId() == null) {
            // Aceasta este o logică simplă de generare a ID-ului,
            // ar trebui mutată în Service. Dar deocamdată e OK.
            passenger.setId(String.valueOf(passengerStore.size() + 1));
        }
        passengerStore.put(passenger.getId(), passenger);
        return passenger;
    }

    public List<Passenger> findAll() {
        return new ArrayList<>(passengerStore.values());
    }

    public Optional<Passenger> findById(String id) {
        return Optional.ofNullable(passengerStore.get(id));
    }

    public void delete(String id) {
        passengerStore.remove(id);
    }
}