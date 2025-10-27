package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Airplane;
import com.example.flight.flightmanagementproject.services.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airplanes") // Ruta de bază
public class AirplaneController {

    private final AirplaneService service;

    @Autowired
    public AirplaneController(AirplaneService service) {
        this.service = service;
    }

    // GET /airplanes
    @GetMapping
    public List<Airplane> getAllAirplanes() {
        return service.getAllAirplanes();
    }

    // GET /airplanes/{id}
    @GetMapping("/{id}")
    public Airplane getAirplane(@PathVariable String id) throws ResourceNotFoundException {
        return service.getAirplaneById(id);
    }

    // POST /airplanes
    @PostMapping
    public Airplane addAirplane(@RequestBody Airplane airplane) throws RepositoryException {
        return service.addAirplane(airplane);
    }

    // DELETE /airplanes/{id}
    @DeleteMapping("/{id}")
    public void deleteAirplane(@PathVariable String id) throws RepositoryException {
        service.deleteAirplane(id);
    }
}