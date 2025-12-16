package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirportEmployee;
import com.example.flight.flightmanagementproject.repositories.AirportEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportEmployeeService {

    private final AirportEmployeeRepository repository;

    @Autowired
    public AirportEmployeeService(AirportEmployeeRepository repository) {
        this.repository = repository;
    }

    // --- METODA ACTUALIZATĂ ---
    public List<AirportEmployee> getAll(String keyword, String sortField, String sortDir) {
        if (sortField == null || sortField.isEmpty()) sortField = "id";
        Sort sort = Sort.by(sortField);
        sort = "desc".equals(sortDir) ? sort.descending() : sort.ascending();

        // Apelăm metoda de căutare actualizată
        return repository.search(keyword, sort);
    }
    // -------------------------

    public List<AirportEmployee> getAll() {
        return repository.findAll();
    }

    public AirportEmployee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Angajat Aeroport negăsit cu ID: " + id));
    }

    public void save(AirportEmployee employee) {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele este obligatoriu.");
        }
        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Departamentul este obligatoriu.");
        }
        if (employee.getDesignation() == null || employee.getDesignation().trim().isEmpty()) {
            throw new IllegalArgumentException("Funcția este obligatorie.");
        }
        repository.save(employee);
    }

    public void update(Long id, AirportEmployee updatedEmployee) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Angajat Aeroport negăsit cu ID: " + id);
        }
        if (updatedEmployee.getName() == null || updatedEmployee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele nu poate fi gol.");
        }

        AirportEmployee existing = getById(id);
        existing.setName(updatedEmployee.getName());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setDesignation(updatedEmployee.getDesignation());
        repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate șterge. Angajat Aeroport negăsit cu ID: " + id);
        }
        repository.deleteById(id);
    }
}