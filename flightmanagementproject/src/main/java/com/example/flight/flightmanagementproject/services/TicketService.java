package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Ticket;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final AbstractRepository<Ticket, String> ticketRepository;

    @Autowired
    public TicketService(@Qualifier("ticketRepository") AbstractRepository<Ticket, String> ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket addTicket(Ticket ticket) throws RepositoryException {
        if (ticket.getId() == null || ticket.getId().isEmpty()) {
            ticket.setId(UUID.randomUUID().toString());
        }
        // Inițializăm lista de bagaje dacă este null
        if (ticket.getLuggages() == null) {
            ticket.setLuggages(new ArrayList<>());
        }
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(String id) throws ResourceNotFoundException {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " not found."));
    }

    public void deleteTicket(String id) throws RepositoryException {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket with id " + id + " not found.");
        }
        ticketRepository.deleteById(id);
    }
}