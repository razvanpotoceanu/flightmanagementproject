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
        // VALIDARE 1: Câmpuri Obligatorii
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele angajatului este obligatoriu.");
        }
        if (employee.getRole() == null) {
            throw new IllegalArgumentException("Rolul angajatului este obligatoriu.");
        }

        // VALIDARE 2 (ID): Dacă cumva obiectul vine cu un ID setat, ne asigurăm că nu există deja
        // (Evităm suprascrierea accidentală a unui angajat existent prin metoda de creare)
        if (employee.getId() != null && repository.existsById(employee.getId())) {
            throw new IllegalArgumentException("Eroare: Există deja un angajat Airline cu ID-ul " + employee.getId() + ". Folosiți funcția de actualizare.");
        }

        repository.save(employee);
    }

    public void update(Long id, AirlineEmployee updatedEmployee) {
        // VALIDARE ID: Trebuie să existe ca să facem update
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Angajat Airline negăsit cu ID: " + id);
        }

        // Validare date
        if (updatedEmployee.getName() == null || updatedEmployee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele nu poate fi gol la actualizare.");
        }

        // Preluăm obiectul existent pentru a păstra consistența
        AirlineEmployee existing = getById(id);

        existing.setName(updatedEmployee.getName());
        existing.setRole(updatedEmployee.getRole());
        // (Dacă ai assignments, le gestionezi aici, sau le lași așa cum erau)

        repository.save(existing);
    }

    public void delete(Long id) {
        // VALIDARE ID: Trebuie să existe ca să facem delete
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate șterge. Angajat Airline negăsit cu ID: " + id);
        }
        repository.deleteById(id);
    }
}