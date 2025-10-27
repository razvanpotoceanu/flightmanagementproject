package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Airplane;
import org.springframework.stereotype.Repository;

@Repository
public class AirplaneRepository extends InMemoryRepository<Airplane, String> {

    // Aici poți adăuga date inițiale dacă dorești, de ex:
    public AirplaneRepository() {
        // Airplane p1 = new Airplane("uuid-1", 747, null, "Boeing 747");
        // store.add(p1);
    }
}