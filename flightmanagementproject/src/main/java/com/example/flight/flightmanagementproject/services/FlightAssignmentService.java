package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.FlightAssignment;
import com.example.flight.flightmanagementproject.repositories.FlightAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightAssignmentService {

    private final FlightAssignmentRepository repository;

    @Autowired
    public FlightAssignmentService(FlightAssignmentRepository repository) {
        this.repository = repository;
    }

    public List<FlightAssignment> getAllFlightAssignments() {
        return repository.findAll();
    }

    public FlightAssignment getFlightAssignmentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignarea nu a fost găsită cu id: " + id));
    }

    public void saveFlightAssignment(FlightAssignment assignment) {
        // Aici am putea adăuga o validare: să ne asigurăm că MĂCAR UN angajat este selectat
        repository.save(assignment);
    }

    public void updateFlightAssignment(Long id, FlightAssignment updatedAssignment) {
        FlightAssignment existing = getFlightAssignmentById(id);

        existing.setFlight(updatedAssignment.getFlight());
        // Actualizăm ambele posibilități
        existing.setAirlineEmployee(updatedAssignment.getAirlineEmployee());
        existing.setAirportEmployee(updatedAssignment.getAirportEmployee());

        repository.save(existing);
    }

    public void deleteFlightAssignment(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Asignarea nu a fost găsită cu id: " + id);
        }
        repository.deleteById(id);
    }
}