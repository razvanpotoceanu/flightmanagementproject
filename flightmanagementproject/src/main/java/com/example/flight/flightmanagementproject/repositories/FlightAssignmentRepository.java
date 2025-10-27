package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.FlightAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class FlightAssignmentRepository extends InMemoryRepository<FlightAssignment, String> {
    // Toată logica (save, findById, findAll, deleteById)
    // este moștenită din InMemoryRepository.
}