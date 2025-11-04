package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import com.example.flight.flightmanagementproject.models.AirportEmployee;
import com.example.flight.flightmanagementproject.models.Staff;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class StaffRepository extends InMemoryRepository<Staff, String> {

    public StaffRepository() {
        // Adăugăm un angajat de tip 'AirlineEmployee'
        AirlineEmployee pilot = new AirlineEmployee(
                UUID.randomUUID().toString(), // ID
                "Traian Vuia",               // Nume
                "Pilot",                     // Rol
                new ArrayList<>()            // Listă goală de zboruri asignate
        );

        // Adăugăm un angajat de tip 'AirportEmployee'
        AirportEmployee securitate = new AirportEmployee(
                UUID.randomUUID().toString(), // ID
                "Aurel Vlaicu",              // Nume
                "Șef Securitate",            // Funcție (Designation)
                "Securitate Aeroportuară"    // Departament
        );

        store.add(pilot);
        store.add(securitate);
    }
}