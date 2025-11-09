package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends InFileRepository<Ticket, String> {

    /**
     * Constructorul apelează clasa părinte (InFileRepository)
     * și îi spune ce fișier JSON să folosească și ce tip de clasă să citească.
     */
    public TicketRepository() {
        super("data/tickets.json", Ticket.class);
    }
}