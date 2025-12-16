package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Luggage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LuggageRepository extends JpaRepository<Luggage, Long> {

    @Query("SELECT l FROM Luggage l " +
            "LEFT JOIN l.ticket t " +
            "WHERE :keyword IS NULL OR :keyword = '' OR " +
            "CAST(l.id AS string) LIKE %:keyword% OR " +
            "LOWER(l.status) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.seatNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Luggage> search(@Param("keyword") String keyword, Sort sort);
}