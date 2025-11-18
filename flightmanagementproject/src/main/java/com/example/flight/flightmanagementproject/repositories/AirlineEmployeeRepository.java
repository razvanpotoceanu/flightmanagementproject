package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineEmployeeRepository extends JpaRepository<AirlineEmployee, Long> {
}