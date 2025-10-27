package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends InMemoryRepository<Ticket, String> {
    // Toată logica (save, findById, findAll, deleteById)
    // este moștenită din InMemoryRepository.
}