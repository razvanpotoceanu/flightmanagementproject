package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Flight;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Căutare după numărul zborului
    List<Flight> findByFlightNumberContainingIgnoreCase(String flightNumber, Sort sort);

    boolean existsByFlightNumber(String flightNumber);
}