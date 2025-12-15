package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Airplane;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    // Căutare după numele modelului
    List<Airplane> findByModelNameContainingIgnoreCase(String modelName, Sort sort);
}