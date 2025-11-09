package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.FlightAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class FlightAssignmentRepository extends InFileRepository<FlightAssignment, String> {

    /**
     * Constructorul apelează clasa părinte (InFileRepository)
     * și îi spune ce fișier JSON să folosească și ce tip de clasă să citească.
     */
    public FlightAssignmentRepository() {
        super("data/flightassignments.json", FlightAssignment.class);
    }
}