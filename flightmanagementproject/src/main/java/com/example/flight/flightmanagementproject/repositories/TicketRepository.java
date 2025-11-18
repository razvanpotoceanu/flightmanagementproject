package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {}