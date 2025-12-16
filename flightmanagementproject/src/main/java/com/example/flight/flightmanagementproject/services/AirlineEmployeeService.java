package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import com.example.flight.flightmanagementproject.repositories.AirlineEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineEmployeeService {

    private final AirlineEmployeeRepository repository;

    @Autowired
    public AirlineEmployeeService(AirlineEmployeeRepository repository) {
        this.repository = repository;
    }

    // --- METODA ACTUALIZATĂ PENTRU PROIECTUL 5 ---
    public List<AirlineEmployee> getAll(String keyword, String sortField, String sortDir) {
        // Configurare Sortare
        if (sortField == null || sortField.isEmpty()) {
            sortField = "id";
        }
        Sort sort = Sort.by(sortField);
        sort = "desc".equals(sortDir) ? sort.descending() : sort.ascending();

        // Apelăm metoda de căutare din Repository (care caută acum și după Rol)
        return repository.search(keyword, sort);
    }
    // ---------------------------------------------

    // Metoda veche (compatibilitate)
    public List<AirlineEmployee> getAll() {
        return repository.findAll();
    }

    public AirlineEmployee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Angajat Airline negăsit cu ID: " + id));
    }

    public void save(AirlineEmployee employee) {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele angajatului este obligatoriu.");
        }
        if (employee.getRole() == null) {
            throw new IllegalArgumentException("Rolul angajatului este obligatoriu.");
        }
        repository.save(employee);
    }

    public void update(Long id, AirlineEmployee updatedEmployee) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Angajat Airline negăsit cu ID: " + id);
        }

        if (updatedEmployee.getName() == null || updatedEmployee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele nu poate fi gol.");
        }

        AirlineEmployee existing = getById(id);
        existing.setName(updatedEmployee.getName());
        existing.setRole(updatedEmployee.getRole());

        repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate șterge. Angajat Airline negăsit cu ID: " + id);
        }
        repository.deleteById(id);
    }
}