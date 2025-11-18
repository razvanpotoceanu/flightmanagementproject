package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Luggage;
import com.example.flight.flightmanagementproject.repositories.LuggageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuggageService {

    private final LuggageRepository repository;

    @Autowired
    public LuggageService(LuggageRepository repository) {
        this.repository = repository;
    }

    public List<Luggage> getAllLuggages() {
        return repository.findAll();
    }

    public Luggage getLuggageById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Luggage not found with id: " + id));
    }

    public void saveLuggage(Luggage luggage) {
        repository.save(luggage);
    }

    public void updateLuggage(Long id, Luggage updatedLuggage) {
        Luggage existing = getLuggageById(id);
        existing.setTicket(updatedLuggage.getTicket());
        existing.setStatus(updatedLuggage.getStatus());
        repository.save(existing);
    }

    public void deleteLuggage(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Luggage not found with id: " + id);
        }
        repository.deleteById(id);
    }
}