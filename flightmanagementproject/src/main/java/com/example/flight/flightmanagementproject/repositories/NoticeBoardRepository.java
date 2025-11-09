package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.NoticeBoard;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeBoardRepository extends InFileRepository<NoticeBoard, String> {

    /**
     * Constructorul apelează clasa părinte (InFileRepository)
     * și îi spune ce fișier JSON să folosească și ce tip de clasă să citească.
     */
    public NoticeBoardRepository() {
        super("data/noticeboards.json", NoticeBoard.class);
    }
}