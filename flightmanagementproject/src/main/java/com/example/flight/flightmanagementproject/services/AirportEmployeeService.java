package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirportEmployee;
import com.example.flight.flightmanagementproject.repositories.AirportEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportEmployeeService {

    private final AirportEmployeeRepository repository;

    @Autowired
    public AirportEmployeeService(AirportEmployeeRepository repository) {
        this.repository = repository;
    }

    public List<AirportEmployee> getAll() {
        return repository.findAll();
    }

    public AirportEmployee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Angajat Aeroport negăsit cu ID: " + id));
    }

    public void save(AirportEmployee employee) {
        repository.save(employee);
    }

    public void update(Long id, AirportEmployee updatedEmployee) {
        AirportEmployee existing = getById(id);
        existing.setName(updatedEmployee.getName());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setDesignation(updatedEmployee.getDesignation());
        repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Angajat Aeroport negăsit cu ID: " + id);
        }
        repository.deleteById(id);
    }
}