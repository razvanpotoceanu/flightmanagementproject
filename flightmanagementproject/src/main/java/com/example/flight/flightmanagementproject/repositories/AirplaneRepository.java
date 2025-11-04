package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Airplane;
import org.springframework.stereotype.Repository;

import java.util.ArrayList; // Necesar pentru new ArrayList<>()
import java.util.UUID;      // Necesar pentru a genera ID-uri

@Repository
public class AirplaneRepository extends InMemoryRepository<Airplane, String> {

    // Aici este constructorul clasei
    public AirplaneRepository() {
        // Acest cod rulează O SINGURĂ DATĂ, când Spring pornește

        // Creăm Avionul 1
        Airplane avion1 = new Airplane(
                UUID.randomUUID().toString(), // ID
                180,                         // Capacitate (number)
                new ArrayList<>(),           // O listă goală de zboruri
                "Boeing 737"                 // Numele modelului (modelName)
        );

        // Creăm Avionul 2
        Airplane avion2 = new Airplane(
                UUID.randomUUID().toString(),
                300,
                new ArrayList<>(),
                "Airbus A330"
        );

        // Adăugăm avioanele direct în lista 'store' (moștenită de la InMemoryRepository)
        store.add(avion1);
        store.add(avion2);
    }
}