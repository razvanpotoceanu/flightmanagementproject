package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import com.example.flight.flightmanagementproject.repositories.AirlineEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineEmployeeService {

    private final AirlineEmployeeRepository repository;

    @Autowired
    public AirlineEmployeeService(AirlineEmployeeRepository repository) {
        this.repository = repository;
    }

    public List<AirlineEmployee> getAll() {
        return repository.findAll();
    }

    public AirlineEmployee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Angajat Airline negăsit cu ID: " + id));
    }

    public void save(AirlineEmployee employee) {
        repository.save(employee);
    }

    public void update(Long id, AirlineEmployee updatedEmployee) {
        AirlineEmployee existing = getById(id);
        existing.setName(updatedEmployee.getName());
        existing.setRole(updatedEmployee.getRole());
        repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Angajat Airline negăsit cu ID: " + id);
        }
        repository.deleteById(id);
    }
}