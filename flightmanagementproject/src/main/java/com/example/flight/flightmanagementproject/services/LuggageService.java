package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Luggage;
import com.example.flight.flightmanagementproject.repositories.LuggageRepository;
import com.example.flight.flightmanagementproject.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuggageService {

    private final LuggageRepository repository;
    // Avem nevoie de TicketRepository pentru a valida existența biletului
    private final TicketRepository ticketRepository;

    @Autowired
    public LuggageService(LuggageRepository repository, TicketRepository ticketRepository) {
        this.repository = repository;
        this.ticketRepository = ticketRepository;
    }

    public List<Luggage> getAllLuggages() {
        return repository.findAll();
    }

    public Luggage getLuggageById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bagajul nu a fost găsit cu id: " + id));
    }

    public void saveLuggage(Luggage luggage) {
        // VALIDARE: Status obligatoriu
        if (luggage.getStatus() == null) {
            throw new IllegalArgumentException("Statusul bagajului este obligatoriu.");
        }

        // VALIDARE: Biletul asociat trebuie să existe în DB
        if (luggage.getTicket() == null || luggage.getTicket().getId() == null) {
            throw new IllegalArgumentException("Trebuie selectat un bilet valid.");
        }
        if (!ticketRepository.existsById(luggage.getTicket().getId())) {
            throw new IllegalArgumentException("Biletul selectat nu există în baza de date.");
        }

        // VALIDARE ID: Prevenire duplicat la creare
        if (luggage.getId() != null && repository.existsById(luggage.getId())) {
            throw new IllegalArgumentException("Există deja un bagaj cu ID-ul " + luggage.getId() + ".");
        }

        repository.save(luggage);
    }

    public void updateLuggage(Long id, Luggage updatedLuggage) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Bagaj negăsit cu ID: " + id);
        }

        // Validare relație Bilet la update
        if (updatedLuggage.getTicket() == null || updatedLuggage.getTicket().getId() == null) {
            throw new IllegalArgumentException("Trebuie selectat un bilet valid.");
        }
        if (!ticketRepository.existsById(updatedLuggage.getTicket().getId())) {
            throw new IllegalArgumentException("Biletul selectat nu există.");
        }

        if (updatedLuggage.getStatus() == null) {
            throw new IllegalArgumentException("Statusul bagajului este obligatoriu.");
        }

        Luggage existing = getLuggageById(id);
        existing.setTicket(updatedLuggage.getTicket());
        existing.setStatus(updatedLuggage.getStatus());

        repository.save(existing);
    }

    public void deleteLuggage(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate șterge. Bagaj negăsit cu ID: " + id);
        }
        repository.deleteById(id);
    }
}