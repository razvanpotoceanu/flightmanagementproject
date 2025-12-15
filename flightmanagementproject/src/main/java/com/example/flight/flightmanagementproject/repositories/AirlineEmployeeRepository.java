package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineEmployeeRepository extends JpaRepository<AirlineEmployee, Long> {
    List<AirlineEmployee> findByNameContainingIgnoreCase(String name, Sort sort);
}