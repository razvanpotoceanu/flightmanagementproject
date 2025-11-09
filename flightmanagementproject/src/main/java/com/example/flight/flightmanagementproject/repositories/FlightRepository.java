package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Flight;
import org.springframework.stereotype.Repository;

@Repository
public class FlightRepository extends InFileRepository<Flight, String> {

    /**
     * Constructorul apelează clasa părinte (InFileRepository)
     * și îi spune ce fișier JSON să folosească și ce tip de clasă să citească.
     */
    public FlightRepository() {
        super("data/flights.json", Flight.class);
    }
}