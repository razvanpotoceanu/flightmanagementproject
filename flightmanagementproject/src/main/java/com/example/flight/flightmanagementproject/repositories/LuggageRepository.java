package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Luggage;
import org.springframework.stereotype.Repository;

@Repository
public class LuggageRepository extends InMemoryRepository<Luggage, String> {
    // Toată logica (save, findById, findAll, deleteById)
    // este moștenită din InMemoryRepository.
}