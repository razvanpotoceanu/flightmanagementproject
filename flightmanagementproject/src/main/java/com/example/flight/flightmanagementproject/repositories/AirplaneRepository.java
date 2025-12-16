package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Airplane;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    // Căutare după Model, ID sau Capacitate
    @Query("SELECT a FROM Airplane a WHERE " +
            ":keyword IS NULL OR :keyword = '' OR " +
            "LOWER(a.modelName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(a.id AS string) LIKE %:keyword% OR " +
            "CAST(a.capacity AS string) LIKE %:keyword%")
    List<Airplane> search(@Param("keyword") String keyword, Sort sort);
}