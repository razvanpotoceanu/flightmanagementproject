package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Airplane;
import com.example.flight.flightmanagementproject.repositories.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirplaneService {

    private final AirplaneRepository repository;

    @Autowired
    public AirplaneService(AirplaneRepository repository) {
        this.repository = repository;
    }

    /**
     * Metoda actualizată pentru Proiectul 5: Suportă sortare și filtrare.
     */
    public List<Airplane> getAllAirplanes(String keyword, String sortField, String sortDir) {
        // 1. Configurare Sortare
        // Dacă sortField e null sau gol, sortăm implicit după 'id'
        if (sortField == null || sortField.isEmpty()) {
            sortField = "id";
        }

        // Creăm obiectul Sort în funcție de direcție (asc/desc)
        Sort sort = Sort.by(sortField);
        sort = "desc".equals(sortDir) ? sort.descending() : sort.ascending();

        // 2. Configurare Filtrare (Căutare)
        if (keyword != null && !keyword.isEmpty()) {
            // Dacă există un cuvânt cheie, căutăm după model (folosind metoda din Repository)
            return repository.findByModelNameContainingIgnoreCase(keyword, sort);
        }

        // 3. Dacă nu e filtrare, returnăm totul sortat
        return repository.findAll(sort);
    }

    // Metoda veche (fără argumente) - o păstrăm pentru compatibilitate sau o ștergem
    // E mai sigur să o păstrezi dacă ai alte părți din cod care o apelează fără parametri
    public List<Airplane> getAllAirplanes() {
        return repository.findAll();
    }

    public Airplane getAirplaneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion negăsit cu ID: " + id));
    }

    public void saveAirplane(Airplane airplane) {
        // Validare 1: Date de intrare
        if (airplane.getModelName() == null || airplane.getModelName().trim().isEmpty()) {
            throw new IllegalArgumentException("Modelul avionului este obligatoriu.");
        }
        if (airplane.getCapacity() == null || airplane.getCapacity() < 10) {
            throw new IllegalArgumentException("Capacitatea trebuie să fie de cel puțin 10 locuri.");
        }

        // Validare 2 (ID): Protecție la duplicat pe ID
        if (airplane.getId() != null && repository.existsById(airplane.getId())) {
            throw new IllegalArgumentException("Eroare: Există deja un avion cu ID-ul " + airplane.getId() + ".");
        }

        repository.save(airplane);
    }

    public void updateAirplane(Long id, Airplane updatedAirplane) {
        // Validare ID
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Avion negăsit cu ID: " + id);
        }

        if (updatedAirplane.getCapacity() == null || updatedAirplane.getCapacity() < 10) {
            throw new IllegalArgumentException("Capacitatea incorectă la actualizare.");
        }

        Airplane existing = getAirplaneById(id);
        existing.setModelName(updatedAirplane.getModelName());
        existing.setCapacity(updatedAirplane.getCapacity());

        repository.save(existing);
    }

    public void deleteAirplane(Long id) {
        // Validare ID
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate șterge. Avion negăsit cu ID: " + id);
        }
        repository.deleteById(id);
    }
}