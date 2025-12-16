package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineEmployeeRepository extends JpaRepository<AirlineEmployee, Long> {

    // Căutare după Nume SAU Rol
    // Pentru Enum, convertim la String pentru a putea folosi LIKE
    @Query("SELECT e FROM AirlineEmployee e WHERE " +
            ":keyword IS NULL OR :keyword = '' OR " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.role) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AirlineEmployee> search(@Param("keyword") String keyword, Sort sort);
}