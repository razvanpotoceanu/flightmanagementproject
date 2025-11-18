package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.AirportEmployee;
import org.springframework.stereotype.Repository;

@Repository
public class AirportEmployeeRepository extends InFileRepository<AirportEmployee, String> {
    public AirportEmployeeRepository() {
        super("data/airport_employees.json", AirportEmployee.class);
    }
}