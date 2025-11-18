package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.AirportEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportEmployeeRepository extends JpaRepository<AirportEmployee, Long> {
}