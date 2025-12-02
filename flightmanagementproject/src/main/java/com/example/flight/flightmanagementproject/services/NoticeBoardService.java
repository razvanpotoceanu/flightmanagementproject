package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.NoticeBoard;
import com.example.flight.flightmanagementproject.repositories.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeBoardService {

    private final NoticeBoardRepository repository;

    @Autowired
    public NoticeBoardService(NoticeBoardRepository repository) {
        this.repository = repository;
    }

    public List<NoticeBoard> getAllNoticeBoards() {
        return repository.findAll();
    }

    public NoticeBoard getNoticeBoardById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panou negăsit cu id: " + id));
    }

    public void saveNoticeBoard(NoticeBoard noticeBoard) {
        // VALIDARE: Data obligatorie
        if (noticeBoard.getDate() == null) {
            throw new IllegalArgumentException("Data panoului este obligatorie.");
        }

        // VALIDARE: ID Duplicat la creare
        if (noticeBoard.getId() != null && repository.existsById(noticeBoard.getId())) {
            throw new IllegalArgumentException("Există deja un panou cu ID-ul " + noticeBoard.getId());
        }

        // VALIDARE BUSINESS (Opțional): Verificăm dacă există deja un panou pentru această dată?
        // De obicei un aeroport are un singur panou de plecări pe zi (sau logică similară)
        /* boolean dateExists = repository.findAll().stream()
             .anyMatch(nb -> nb.getDate().equals(noticeBoard.getDate()));
        if (dateExists) {
             throw new IllegalArgumentException("Există deja un panou pentru data: " + noticeBoard.getDate());
        }
        */

        repository.save(noticeBoard);
    }

    public void updateNoticeBoard(Long id, NoticeBoard updatedNoticeBoard) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Panou negăsit cu ID: " + id);
        }

        if (updatedNoticeBoard.getDate() == null) {
            throw new IllegalArgumentException("Data panoului este obligatorie.");
        }

        NoticeBoard existing = getNoticeBoardById(id);
        existing.setDate(updatedNoticeBoard.getDate());

        repository.save(existing);
    }

    public void deleteNoticeBoard(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate șterge. Panou negăsit cu ID: " + id);
        }
        repository.deleteById(id);
    }
}