package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Ticket;
import com.example.flight.flightmanagementproject.repositories.FlightRepository;
import com.example.flight.flightmanagementproject.repositories.PassengerRepository;
import com.example.flight.flightmanagementproject.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repository;
    // Avem nevoie de aceste repo-uri pentru a valida existența FK
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public TicketService(TicketRepository repository,
                         PassengerRepository passengerRepository,
                         FlightRepository flightRepository) {
        this.repository = repository;
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
    }

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bilet nu a fost găsit cu id: " + id));
    }

    public void saveTicket(Ticket ticket) {
        validateTicket(ticket);

        // Validare ID
        if (ticket.getId() != null && repository.existsById(ticket.getId())) {
            throw new IllegalArgumentException("Există deja un bilet cu acest ID.");
        }

        repository.save(ticket);
    }

    public void updateTicket(Long id, Ticket updatedTicket) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Bilet negăsit cu ID: " + id);
        }

        validateTicket(updatedTicket);

        Ticket existing = getTicketById(id);
        existing.setPrice(updatedTicket.getPrice());
        existing.setSeatNumber(updatedTicket.getSeatNumber());
        existing.setPassenger(updatedTicket.getPassenger());
        existing.setFlight(updatedTicket.getFlight());

        repository.save(existing);
    }

    private void validateTicket(Ticket ticket) {
        // 1. Validare câmpuri simple
        if (ticket.getPrice() <= 0) {
            throw new IllegalArgumentException("Prețul trebuie să fie pozitiv.");
        }
        if (ticket.getSeatNumber() == null || ticket.getSeatNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Locul este obligatoriu.");
        }

        // 2. Validare Relații (Pasager și Zbor)
        if (ticket.getPassenger() == null || ticket.getPassenger().getId() == null) {
            throw new IllegalArgumentException("Trebuie selectat un pasager.");
        }
        if (!passengerRepository.existsById(ticket.getPassenger().getId())) {
            throw new IllegalArgumentException("Pasagerul selectat nu există în baza de date.");
        }

        if (ticket.getFlight() == null || ticket.getFlight().getId() == null) {
            throw new IllegalArgumentException("Trebuie selectat un zbor.");
        }
        if (!flightRepository.existsById(ticket.getFlight().getId())) {
            throw new IllegalArgumentException("Zborul selectat nu există în baza de date.");
        }
    }

    public void deleteTicket(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate șterge. Bilet negăsit cu ID: " + id);
        }
        repository.deleteById(id);
    }
}