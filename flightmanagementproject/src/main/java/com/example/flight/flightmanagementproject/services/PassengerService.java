package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Passenger;
import com.example.flight.flightmanagementproject.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository repository;

    @Autowired
    public PassengerService(PassengerRepository repository) {
        this.repository = repository;
    }

    /**
     * Metoda nouă pentru Proiectul 5: Găsește toți pasagerii cu suport pentru Filtrare și Sortare.
     * @param keyword Cuvântul cheie pentru căutare (poate fi null).
     * @param sortField Câmpul după care se sortează (ex: "id", "name", "email").
     * @param sortDir Direcția sortării ("asc" sau "desc").
     */
    public List<Passenger> getAllPassengers(String keyword, String sortField, String sortDir) {
        // 1. Configurăm obiectul Sort
        Sort sort = Sort.by(sortField);
        sort = "desc".equalsIgnoreCase(sortDir) ? sort.descending() : sort.ascending();

        // 2. Aplicăm filtrarea dacă există un cuvânt cheie
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Căutăm după nume sau email (ignorând case-ul) și aplicăm sortarea
            return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, sort);
        }

        // 3. Dacă nu există filtrare, returnăm totul sortat
        return repository.findAll(sort);
    }

    // Metodă veche de compatibilitate (o poți păstra sau șterge dacă nu mai e folosită)
    public List<Passenger> getAllPassengers() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Passenger getPassengerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pasagerul nu a fost găsit cu id: " + id));
    }

    public void savePassenger(Passenger passenger) {
        // VALIDARE: Email unic la creare
        if (passenger.getId() == null && repository.existsByEmail(passenger.getEmail())) {
            throw new IllegalArgumentException("Există deja un pasager cu acest email: " + passenger.getEmail());
        }

        repository.save(passenger);
    }

    public void updatePassenger(Long id, Passenger updatedPassenger) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Pasager negăsit cu ID: " + id);
        }

        Passenger existing = getPassengerById(id);

        // VALIDARE: Email unic la update (doar dacă s-a schimbat)
        if (!existing.getEmail().equalsIgnoreCase(updatedPassenger.getEmail()) &&
                repository.existsByEmail(updatedPassenger.getEmail())) {
            throw new IllegalArgumentException("Emailul " + updatedPassenger.getEmail() + " este deja folosit de alt pasager.");
        }

        existing.setName(updatedPassenger.getName());
        existing.setEmail(updatedPassenger.getEmail());
        existing.setCurrency(updatedPassenger.getCurrency());

        repository.save(existing);
    }

    public void deletePassenger(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pasagerul nu a fost găsit cu id: " + id);
        }
        repository.deleteById(id);
    }
}