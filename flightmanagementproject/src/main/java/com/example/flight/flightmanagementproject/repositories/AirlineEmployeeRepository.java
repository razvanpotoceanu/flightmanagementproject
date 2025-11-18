package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import org.springframework.stereotype.Repository;

@Repository
public class AirlineEmployeeRepository extends InFileRepository<AirlineEmployee, String> {
    public AirlineEmployeeRepository() {
        super("data/airline_employees.json", AirlineEmployee.class);
    }
}