package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Passenger;
import com.example.flight.flightmanagementproject.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository repository;

    @Autowired
    public PassengerService(PassengerRepository repository) {
        this.repository = repository;
    }

    public List<Passenger> getAllPassengers() {
        return repository.findAll();
    }

    public Passenger getPassengerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));
    }

    // Metoda de salvare nu mai returnează void, poate returna entitatea salvată
    public Passenger savePassenger(Passenger passenger) {
        // Nu generăm ID, MySQL o face (Auto Increment)
        return repository.save(passenger);
    }

    public Passenger updatePassenger(Long id, Passenger updatedPassenger) {
        Passenger existing = getPassengerById(id);
        existing.setName(updatedPassenger.getName());
        existing.setEmail(updatedPassenger.getEmail());
        existing.setCurrency(updatedPassenger.getCurrency());
        return repository.save(existing);
    }

    public void deletePassenger(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Passenger not found with id: " + id);
        }
        repository.deleteById(id);
    }
}