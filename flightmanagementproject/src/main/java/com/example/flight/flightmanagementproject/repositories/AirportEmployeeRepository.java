package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.AirportEmployee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportEmployeeRepository extends JpaRepository<AirportEmployee, Long> {
    List<AirportEmployee> findByNameContainingIgnoreCase(String name, Sort sort);
}