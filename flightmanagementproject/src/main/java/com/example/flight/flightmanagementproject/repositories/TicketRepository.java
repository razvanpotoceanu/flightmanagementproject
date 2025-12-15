package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Căutare după număr loc sau numele pasagerului
    @Query("SELECT t FROM Ticket t " +
            "LEFT JOIN t.passenger p " +
            "WHERE :keyword IS NULL OR :keyword = '' OR " +
            "LOWER(t.seatNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Ticket> search(@Param("keyword") String keyword, Sort sort);
}