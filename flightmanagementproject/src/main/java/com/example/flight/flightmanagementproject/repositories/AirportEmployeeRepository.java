package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.AirportEmployee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportEmployeeRepository extends JpaRepository<AirportEmployee, Long> {

    // Căutare după Nume sau Departament
    @Query("SELECT e FROM AirportEmployee e WHERE " +
            ":keyword IS NULL OR :keyword = '' OR " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AirportEmployee> search(@Param("keyword") String keyword, Sort sort);
}