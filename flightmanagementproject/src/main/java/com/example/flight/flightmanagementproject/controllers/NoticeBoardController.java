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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("noticeboards", service.getAllNoticeBoards());
        return "noticeboard/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("noticeboard", new NoticeBoard());
        return "noticeboard/form";
    }

    @PostMapping
    public String addNoticeBoard(@Valid @ModelAttribute NoticeBoard noticeBoard, BindingResult result) {
        if (result.hasErrors()) {
            return "noticeboard/form";
        }
        service.saveNoticeBoard(noticeBoard);
        return "redirect:/noticeboards";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("noticeboard", service.getNoticeBoardById(id));
            return "noticeboard/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/noticeboards";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateNoticeBoard(@PathVariable Long id, @Valid @ModelAttribute NoticeBoard noticeBoard, BindingResult result) {
        if (result.hasErrors()) {
            noticeBoard.setId(id);
            return "noticeboard/edit-form";
        }
        service.updateNoticeBoard(id, noticeBoard);
        return "redirect:/noticeboards";
    }

    @PostMapping("/{id}/delete")
    public String deleteNoticeBoard(@PathVariable Long id) {
        service.deleteNoticeBoard(id);
        return "redirect:/noticeboards";
    }

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