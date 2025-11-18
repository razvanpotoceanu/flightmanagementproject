package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AirlineEmployeeService {

    private final AbstractRepository<AirlineEmployee, String> repository;

    @Autowired
    public AirlineEmployeeService(@Qualifier("airlineEmployeeRepository") AbstractRepository<AirlineEmployee, String> repository) {
        this.repository = repository;
    }

    public List<AirlineEmployee> getAll() {
        return repository.findAll();
    }

    public AirlineEmployee getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Angajat nu a fost găsit: " + id));
    }

    public void add(AirlineEmployee employee) throws RepositoryException {
        if (employee.getId() == null || employee.getId().isEmpty()) {
            employee.setId(UUID.randomUUID().toString());
        }
        if (employee.getAssignments() == null) {
            employee.setAssignments(new ArrayList<>());
        }
        repository.save(employee);
    }

    public void update(String id, AirlineEmployee employee) throws RepositoryException {
        employee.setId(id);
        if (employee.getAssignments() == null) {
            employee.setAssignments(new ArrayList<>());
        }
        repository.save(employee);
    }

    public void delete(String id) throws RepositoryException {
        repository.deleteById(id);
    }
}