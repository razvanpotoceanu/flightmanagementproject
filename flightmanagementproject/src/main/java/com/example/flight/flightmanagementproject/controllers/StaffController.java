package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Staff;
import com.example.flight.flightmanagementproject.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff") // Ruta de bază
public class StaffController {

    private final StaffService service;

    @Autowired
    public StaffController(StaffService service) {
        this.service = service;
    }

    // GET /staff
    @GetMapping
    public List<Staff> getAllStaff() {
        return service.getAllStaff();
    }

    // GET /staff/{id}
    @GetMapping("/{id}")
    public Staff getStaff(@PathVariable String id) throws ResourceNotFoundException {
        return service.getStaffById(id);
    }

    // POST /staff
    @PostMapping
    public Staff addStaff(@RequestBody Staff staff) throws RepositoryException {
        // Datorită @JsonTypeInfo din clasa Staff, Spring/Jackson
        // va ști automat dacă să creeze AirlineEmployee sau AirportEmployee
        return service.addStaff(staff);
    }

    // DELETE /staff/{id}
    @DeleteMapping("/{id}")
    public void deleteStaff(@PathVariable String id) throws RepositoryException {
        service.deleteStaff(id);
    }
}