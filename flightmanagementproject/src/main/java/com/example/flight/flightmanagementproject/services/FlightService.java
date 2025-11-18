package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private final FlightRepository repository;

    @Autowired
    public FlightService(FlightRepository repository) {
        this.repository = repository;
    }

    public List<Flight> getAllFlights() {
        return repository.findAll();
    }

    public Flight getFlightById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
    }

    public void saveFlight(Flight flight) {
        // ID-ul este generat automat de baza de date
        repository.save(flight);
    }

    public void updateFlight(Long id, Flight updatedFlight) {
        Flight existing = getFlightById(id);

        existing.setFlightNumber(updatedFlight.getFlightNumber());
        existing.setDepartureTime(updatedFlight.getDepartureTime());
        existing.setArrivalTime(updatedFlight.getArrivalTime());
        existing.setAirplane(updatedFlight.getAirplane());
        existing.setNoticeBoard(updatedFlight.getNoticeBoard());

        repository.save(existing);
    }

    public void deleteFlight(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Flight not found with id: " + id);
        }
        repository.deleteById(id);
    }
}