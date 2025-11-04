package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class FlightRepository extends InMemoryRepository<Flight, String> {

    public FlightRepository() {

        // Adăugăm Zborul 1 (folosind constructorul complet)
        Flight zborBucuresti = new Flight(
                UUID.randomUUID().toString(),    // ID
                "RO-201",                        // flightNumber
                "08:00 AM",                      // departureTime
                "09:00 AM"                       // arrivalTime
        );

        // Adăugăm Zborul 2 (folosind constructorul complet)
        Flight zborLondra = new Flight(
                UUID.randomUUID().toString(),    // ID
                "WIZZ-404",                      // flightNumber
                "10:30 AM",                      // departureTime
                "12:00 PM"                       // arrivalTime
        );

        // Adăugăm ambele zboruri în lista 'store' moștenită
        store.add(zborBucuresti);
        store.add(zborLondra);
    }
}