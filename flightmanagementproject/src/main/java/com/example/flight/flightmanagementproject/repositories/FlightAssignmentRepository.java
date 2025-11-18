package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.FlightAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightAssignmentRepository extends JpaRepository<FlightAssignment, Long> {
}