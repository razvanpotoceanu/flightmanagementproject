package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class PassengerRepository extends InMemoryRepository<Passenger, String> {

    public PassengerRepository() {
        // Adăugăm 2 pasageri la pornire
        Passenger p1 = new Passenger(
                UUID.randomUUID().toString(), // ID
                "Mihai Eminescu",            // Nume
                "RON",                       // Valută
                new ArrayList<>(),           // O listă goală de bilete
                "mihai.eminescu@test.com"    // Email
        );

        Passenger p2 = new Passenger(
                UUID.randomUUID().toString(),
                "Veronica Micle",
                "EUR",
                new ArrayList<>(),
                "veronica.micle@test.com"
        );

        store.add(p1);
        store.add(p2);
    }
}