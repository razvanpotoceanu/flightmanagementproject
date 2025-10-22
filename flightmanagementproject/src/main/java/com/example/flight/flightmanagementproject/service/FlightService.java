package com.example.flight.flightmanagementproject.service;

import com.example.flight.flightmanagementproject.model.Flight;
import com.example.flight.flightmanagementproject.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    // Ai putea injecta și alte repository-uri (ex: StaffRepository)

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(String id) {
        return flightRepository.findById(id);
    }

    public Flight createFlight(String name, String departure, String arrival) {
        String newId = UUID.randomUUID().toString();
        // Creăm un zbor nou, gol.
        Flight flight = new Flight(
                newId, name, null, null,
                new ArrayList<>(), new ArrayList<>(),
                departure, arrival);
        return flightRepository.save(flight);
    }
}