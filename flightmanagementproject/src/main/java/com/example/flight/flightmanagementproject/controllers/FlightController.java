package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights") // Ruta de bază
public class FlightController {

    private final FlightService service;

    @Autowired
    public FlightController(FlightService service) {
        this.service = service;
    }

    // GET /flights
    @GetMapping
    public List<Flight> getAllFlights() {
        return service.getAllFlights();
    }

    // GET /flights/{id}
    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable String id) throws ResourceNotFoundException {
        return service.getFlightById(id);
    }

    // POST /flights
    @PostMapping
    public Flight addFlight(@RequestBody Flight flight) throws RepositoryException {
        return service.addFlight(flight);
    }

    // DELETE /flights/{id}
    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable String id) throws RepositoryException {
        service.deleteFlight(id);
    }
}