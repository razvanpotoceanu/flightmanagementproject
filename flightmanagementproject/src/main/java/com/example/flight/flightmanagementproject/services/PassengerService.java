package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Passenger;
import com.example.flight.flightmanagementproject.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository repository;

    @Autowired
    public PassengerService(PassengerRepository repository) {
        this.repository = repository;
    }

    public List<Passenger> getAllPassengers() {
        return repository.findAll();
    }

    public Passenger getPassengerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pasagerul nu a fost găsit cu id: " + id));
    }

    public void savePassenger(Passenger passenger) {
        // VALIDARE CÂMPURI
        validatePassengerData(passenger);

        // VALIDARE DUPLICAT (La Creare): Email unic
        if (passenger.getId() == null && repository.existsByEmail(passenger.getEmail())) {
            throw new IllegalArgumentException("Există deja un pasager cu acest email: " + passenger.getEmail());
        }
        // VALIDARE ID
        if (passenger.getId() != null && repository.existsById(passenger.getId())) {
            throw new IllegalArgumentException("Există deja un pasager cu acest ID.");
        }

        repository.save(passenger);
    }

    public void updatePassenger(Long id, Passenger updatedPassenger) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Pasager negăsit cu ID: " + id);
        }

        // Validăm datele de intrare
        validatePassengerData(updatedPassenger);

        Passenger existing = getPassengerById(id);

        // VALIDARE DUPLICAT (La Update): Verificăm emailul doar dacă s-a schimbat
        if (!existing.getEmail().equals(updatedPassenger.getEmail()) &&
                repository.existsByEmail(updatedPassenger.getEmail())) {
            throw new IllegalArgumentException("Emailul " + updatedPassenger.getEmail() + " este deja folosit de alt pasager.");
        }

        existing.setName(updatedPassenger.getName());
        existing.setEmail(updatedPassenger.getEmail());
        existing.setCurrency(updatedPassenger.getCurrency());

        repository.save(existing);
    }

    private void validatePassengerData(Passenger p) {
        if (p.getName() == null || p.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele este obligatoriu.");
        }
        if (p.getEmail() == null || p.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Emailul este obligatoriu.");
        }
        if (p.getCurrency() == null || p.getCurrency().trim().isEmpty()) {
            throw new IllegalArgumentException("Valuta este obligatorie.");
        }
    }

    public void deletePassenger(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate șterge. Pasager negăsit cu ID: " + id);
        }
        // Atenție: Ștergerea unui pasager va șterge și biletele (Cascade)
        repository.deleteById(id);
    }
}