package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Luggage;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LuggageService {

    private final AbstractRepository<Luggage, String> luggageRepository;

    @Autowired
    public LuggageService(@Qualifier("luggageRepository") AbstractRepository<Luggage, String> luggageRepository) {
        this.luggageRepository = luggageRepository;
    }

    public Luggage addLuggage(Luggage luggage) throws RepositoryException {
        if (luggage.getId() == null || luggage.getId().isEmpty()) {
            luggage.setId(UUID.randomUUID().toString());
        }
        return luggageRepository.save(luggage);
    }

    public List<Luggage> getAllLuggages() {
        return luggageRepository.findAll();
    }

    public Luggage getLuggageById(String id) throws ResourceNotFoundException {
        return luggageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Luggage with id " + id + " not found."));
    }

    public void deleteLuggage(String id) throws RepositoryException {
        if (!luggageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Luggage with id " + id + " not found.");
        }
        luggageRepository.deleteById(id);
    }
}