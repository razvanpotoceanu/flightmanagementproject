package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Airplane;
import com.example.flight.flightmanagementproject.repositories.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirplaneService {

    private final AirplaneRepository repository;

    @Autowired
    public AirplaneService(AirplaneRepository repository) {
        this.repository = repository;
    }

    public List<Airplane> getAllAirplanes() {
        return repository.findAll();
    }

    public Airplane getAirplaneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airplane not found with id: " + id));
    }

    public void saveAirplane(Airplane airplane) {
        repository.save(airplane);
    }

    public void updateAirplane(Long id, Airplane updatedAirplane) {
        Airplane existing = getAirplaneById(id);

        existing.setModelName(updatedAirplane.getModelName());
        existing.setCapacity(updatedAirplane.getCapacity());

        repository.save(existing);
    }

    public void deleteAirplane(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Airplane not found with id: " + id);
        }
        repository.deleteById(id);
    }
}