package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Airplane;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AirplaneService {

    private final AbstractRepository<Airplane, String> airplaneRepository;

    @Autowired
    public AirplaneService(@Qualifier("airplaneRepository") AbstractRepository<Airplane, String> airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    // Metodă de creare (din vechiul tău cod)
    public Airplane addAirplane(Airplane airplane) throws RepositoryException {
        // Logica de business: ne asigurăm că are un ID nou
        if (airplane.getId() == null || airplane.getId().isEmpty()) {
            airplane.setId(UUID.randomUUID().toString());
        }
        // Inițializăm listele goale
        if (airplane.getFlights() == null) {
            airplane.setFlights(new ArrayList<>());
        }

        return airplaneRepository.save(airplane);
    }

    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    public Airplane getAirplaneById(String id) throws ResourceNotFoundException {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airplane with id " + id + " not found."));
    }

    public void deleteAirplane(String id) throws RepositoryException {
        if (!airplaneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airplane with id " + id + " not found.");
        }
        airplaneRepository.deleteById(id);
    }
}