package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.NoticeBoard;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeBoardRepository extends InMemoryRepository<NoticeBoard, String> {
    // Toată logica (save, findById, findAll, deleteById)
    // este moștenită din InMemoryRepository.
}