package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Luggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LuggageRepository extends JpaRepository<Luggage, Long> {}