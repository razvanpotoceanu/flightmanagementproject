package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.NoticeBoard;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    // Căutare după dată (conversie la String pentru filtrare simplă)
    @Query("SELECT n FROM NoticeBoard n " +
            "WHERE :keyword IS NULL OR :keyword = '' OR " +
            "CAST(n.date AS string) LIKE %:keyword%")
    List<NoticeBoard> search(@Param("keyword") String keyword, Sort sort);
}