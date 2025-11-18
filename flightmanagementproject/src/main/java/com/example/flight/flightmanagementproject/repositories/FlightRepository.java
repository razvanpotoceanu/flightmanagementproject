package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {}