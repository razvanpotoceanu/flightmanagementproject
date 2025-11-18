package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {}