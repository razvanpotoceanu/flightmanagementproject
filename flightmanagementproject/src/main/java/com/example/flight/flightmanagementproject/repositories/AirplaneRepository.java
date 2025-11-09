package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Airplane;
import org.springframework.stereotype.Repository;

@Repository
public class AirplaneRepository extends InFileRepository<Airplane, String> {

    public AirplaneRepository() {
        super("data/airplanes.json", Airplane.class);
    }
}