package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Passenger;
import com.example.flight.flightmanagementproject.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers") // Ruta de bază, ca în "/devices"
public class PassengerController {

    private final PassengerService service;

    @Autowired
    public PassengerController(PassengerService service) {
        this.service = service;
    }

    // GET /passengers
    @GetMapping
    public List<Passenger> getAllPassengers() {
        return service.getAllPassengers();
    }

    // GET /passengers/{id}
    @GetMapping("/{id}")
    public Passenger getPassenger(@PathVariable String id) throws ResourceNotFoundException {
        return service.getPassengerById(id);
    }

    // POST /passengers
    @PostMapping
    public Passenger addPassenger(@RequestBody Passenger passenger) throws RepositoryException {
        // @RequestBody preia JSON-ul din corpul cererii și îl transformă în obiect Passenger
        return service.addPassenger(passenger);
    }

    // DELETE /passengers/{id}
    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable String id) throws ResourceNotFoundException {
        service.deletePassenger(id);
    }
}