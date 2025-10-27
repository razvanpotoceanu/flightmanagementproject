package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerRepository extends InMemoryRepository<Passenger, String> {
    // Implementarea este moștenită
}