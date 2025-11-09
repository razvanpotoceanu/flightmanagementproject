package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.NoticeBoard;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NoticeBoardService {

    private final AbstractRepository<NoticeBoard, String> noticeBoardRepository;

    @Autowired
    public NoticeBoardService(@Qualifier("noticeBoardRepository") AbstractRepository<NoticeBoard, String> noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public NoticeBoard addNoticeBoard(NoticeBoard noticeBoard) throws RepositoryException {
        if (noticeBoard.getId() == null || noticeBoard.getId().isEmpty()) {
            noticeBoard.setId(UUID.randomUUID().toString());
        }
        // Inițializăm lista de zboruri dacă este null
        if (noticeBoard.getFlightsOfTheDay() == null) {
            noticeBoard.setFlightsOfTheDay(new ArrayList<>());
        }
        return noticeBoardRepository.save(noticeBoard);
    }

    public List<NoticeBoard> getAllNoticeBoards() {
        return noticeBoardRepository.findAll();
    }

    public NoticeBoard getNoticeBoardById(String id) throws ResourceNotFoundException {
        return noticeBoardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NoticeBoard with id " + id + " not found."));
    }

    public void deleteNoticeBoard(String id) throws RepositoryException {
        if (!noticeBoardRepository.existsById(id)) {
            throw new ResourceNotFoundException("NoticeBoard with id " + id + " not found.");
        }
        noticeBoardRepository.deleteById(id);
    }
    public NoticeBoard updateNoticeBoard(String id, NoticeBoard noticeBoard) throws RepositoryException {
        // Setăm ID-ul din URL
        noticeBoard.setId(id);

        // Ne asigurăm că lista de zboruri nu devine null la actualizare
        if (noticeBoard.getFlightsOfTheDay() == null) {
            noticeBoard.setFlightsOfTheDay(new ArrayList<>());
        }

        return noticeBoardRepository.save(noticeBoard);
    }


}