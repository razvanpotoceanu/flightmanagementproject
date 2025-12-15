package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.FlightAssignment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightAssignmentRepository extends JpaRepository<FlightAssignment, Long> {

    @Query("SELECT fa FROM FlightAssignment fa " +
            "LEFT JOIN fa.flight f " +
            "LEFT JOIN fa.airlineEmployee ae " +
            "LEFT JOIN fa.airportEmployee ape " +
            "WHERE :keyword IS NULL OR :keyword = '' OR " +
            "LOWER(f.flightNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(ae.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(ape.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<FlightAssignment> search(@Param("keyword") String keyword, Sort sort);
}