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
                .orElseThrow(() -> new ResourceNotFoundException("NoticeBoard not found with id: " + id));
    }

    public void saveNoticeBoard(NoticeBoard noticeBoard) {
        repository.save(noticeBoard);
    }

    public void updateNoticeBoard(Long id, NoticeBoard updatedNoticeBoard) {
        NoticeBoard existing = getNoticeBoardById(id);

        existing.setDate(updatedNoticeBoard.getDate());
        // Lista de zboruri se actualizează de obicei prin relația din partea 'Flight'

        repository.save(existing);
    }

    public void deleteNoticeBoard(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("NoticeBoard not found with id: " + id);
        }
        repository.deleteById(id);
    }
}