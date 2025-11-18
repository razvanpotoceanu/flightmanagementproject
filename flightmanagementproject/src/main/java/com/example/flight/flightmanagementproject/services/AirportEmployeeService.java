package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirportEmployee;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AirportEmployeeService {

    private final AbstractRepository<AirportEmployee, String> repository;

    @Autowired
    public AirportEmployeeService(@Qualifier("airportEmployeeRepository") AbstractRepository<AirportEmployee, String> repository) {
        this.repository = repository;
    }

    public List<AirportEmployee> getAll() {
        return repository.findAll();
    }

    public AirportEmployee getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Angajat nu a fost găsit: " + id));
    }

    public void add(AirportEmployee employee) throws RepositoryException {
        if (employee.getId() == null || employee.getId().isEmpty()) {
            employee.setId(UUID.randomUUID().toString());
        }
        repository.save(employee);
    }

    public void update(String id, AirportEmployee employee) throws RepositoryException {
        employee.setId(id);
        repository.save(employee);
    }

    public void delete(String id) throws RepositoryException {
        repository.deleteById(id);
    }
}