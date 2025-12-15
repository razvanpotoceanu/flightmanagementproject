package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.NoticeBoard;
import com.example.flight.flightmanagementproject.services.NoticeBoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/noticeboards")
public class NoticeBoardController {

    private final NoticeBoardService service;

    @Autowired
    public NoticeBoardController(NoticeBoardService service) {
        this.service = service;
    }

    // 1. LISTARE (Cu Sortare și Filtrare - Proiect 5)
    @GetMapping
    public String list(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        // Trebuie să ai metoda 'getAllNoticeBoards(keyword, sortField, sortDir)' în Service.
        // Dacă nu o ai încă, folosește varianta veche: service.getAllNoticeBoards()
        // Dar pentru Proiectul 5 complet, adaug-o în service!
        model.addAttribute("noticeboards", service.getAllNoticeBoards(keyword, sortField, sortDir));

        // Parametrii pentru View
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "noticeboard/index";
    }

    // 2. FORMULAR CREARE
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("noticeboard", new NoticeBoard());
        return "noticeboard/form";
    }

    // 3. SALVARE
    @PostMapping
    public String addNoticeBoard(@Valid @ModelAttribute NoticeBoard noticeBoard, BindingResult result) {
        if (result.hasErrors()) {
            return "noticeboard/form";
        }
        try {
            service.saveNoticeBoard(noticeBoard);
        } catch (IllegalArgumentException e) {
            // Prindem erorile de business (ex: Data duplicată)
            result.rejectValue("date", "error.noticeBoard", e.getMessage());
            return "noticeboard/form";
        }
        return "redirect:/noticeboards";
    }

    // 4. FORMULAR EDITARE
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("noticeboard", service.getNoticeBoardById(id));
            return "noticeboard/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/noticeboards";
        }
    }

    // 5. UPDATE
    @PostMapping("/{id}/edit")
    public String updateNoticeBoard(@PathVariable Long id, @Valid @ModelAttribute NoticeBoard noticeBoard, BindingResult result) {
        if (result.hasErrors()) {
            noticeBoard.setId(id);
            return "noticeboard/edit-form";
        }
        try {
            service.updateNoticeBoard(id, noticeBoard);
        } catch (IllegalArgumentException e) {
            result.rejectValue("date", "error.noticeBoard", e.getMessage());
            noticeBoard.setId(id);
            return "noticeboard/edit-form";
        }
        return "redirect:/noticeboards";
    }

    // 6. ȘTERGERE
    @PostMapping("/{id}/delete")
    public String deleteNoticeBoard(@PathVariable Long id) {
        service.deleteNoticeBoard(id);
        return "redirect:/noticeboards";
    }

    // 7. DETALII
    @GetMapping("/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("noticeboard", service.getNoticeBoardById(id));
            return "noticeboard/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/noticeboards";
        }
    }
}