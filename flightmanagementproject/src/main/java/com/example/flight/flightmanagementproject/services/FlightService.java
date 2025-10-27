package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FlightService {

    private final AbstractRepository<Flight, String> flightRepository;

    @Autowired
    public FlightService(@Qualifier("flightRepository") AbstractRepository<Flight, String> flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(String id) throws ResourceNotFoundException {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight with id " + id + " not found."));
    }

    // Metodă de creare (din vechiul tău cod)
    public Flight addFlight(Flight flight) throws RepositoryException {
        if (flight.getId() == null || flight.getId().isEmpty()) {
            flight.setId(UUID.randomUUID().toString());
        }
        if (flight.getTickets() == null) {
            flight.setTickets(new ArrayList<>());
        }
        if (flight.getFlightAssignments() == null) {
            flight.setFlightAssignments(new ArrayList<>());
        }

        return flightRepository.save(flight);
    }

    public void deleteFlight(String id) throws RepositoryException {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight with id " + id + " not found.");
        }
        flightRepository.deleteById(id);
    }
}