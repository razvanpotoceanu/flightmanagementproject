package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Ticket;
import com.example.flight.flightmanagementproject.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repository;

    @Autowired
    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
    }

    public void saveTicket(Ticket ticket) {
        repository.save(ticket);
    }

    public void updateTicket(Long id, Ticket updatedTicket) {
        Ticket existing = getTicketById(id);

        existing.setPrice(updatedTicket.getPrice());
        existing.setSeatNumber(updatedTicket.getSeatNumber());
        existing.setPassenger(updatedTicket.getPassenger());
        existing.setFlight(updatedTicket.getFlight());

        repository.save(existing);
    }

    public void deleteTicket(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with id: " + id);
        }
        repository.deleteById(id);
    }
}