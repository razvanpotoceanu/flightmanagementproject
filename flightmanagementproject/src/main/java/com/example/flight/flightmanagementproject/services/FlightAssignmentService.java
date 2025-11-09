package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.FlightAssignment;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FlightAssignmentService {

    private final AbstractRepository<FlightAssignment, String> assignmentRepository;

    @Autowired
    public FlightAssignmentService(@Qualifier("flightAssignmentRepository") AbstractRepository<FlightAssignment, String> assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public FlightAssignment addFlightAssignment(FlightAssignment assignment) throws RepositoryException {
        if (assignment.getId() == null || assignment.getId().isEmpty()) {
            assignment.setId(UUID.randomUUID().toString());
        }
        return assignmentRepository.save(assignment);
    }

    public List<FlightAssignment> getAllFlightAssignments() {
        return assignmentRepository.findAll();
    }

    public FlightAssignment getFlightAssignmentById(String id) throws ResourceNotFoundException {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FlightAssignment with id " + id + " not found."));
    }

    public void deleteFlightAssignment(String id) throws RepositoryException {
        if (!assignmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("FlightAssignment with id " + id + " not found.");
        }
        assignmentRepository.deleteById(id);
    }
    public FlightAssignment updateFlightAssignment(String id, FlightAssignment assignment) throws RepositoryException {
        // Setăm ID-ul din URL
        assignment.setId(id);

        // Această clasă nu are liste de inițializat

        return assignmentRepository.save(assignment);
    }


}