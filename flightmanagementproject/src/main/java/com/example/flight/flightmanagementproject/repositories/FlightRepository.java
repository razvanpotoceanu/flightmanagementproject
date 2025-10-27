package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Flight;
import org.springframework.stereotype.Repository;

@Repository
public class FlightRepository extends InMemoryRepository<Flight, String> {
    // Implementarea este moștenită
}