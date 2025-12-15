package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Passenger;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    // Căutare după nume SAU email, ignorând case-ul, și sortează rezultatul
    List<Passenger> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Sort sort);

    boolean existsByEmail(String email);
}