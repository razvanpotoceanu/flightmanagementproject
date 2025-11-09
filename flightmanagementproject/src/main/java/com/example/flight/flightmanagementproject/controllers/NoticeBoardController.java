package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.NoticeBoard;
import com.example.flight.flightmanagementproject.services.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/noticeboards") // Ruta de bază
public class NoticeBoardController {

    private final NoticeBoardService service;

    @Autowired
    public NoticeBoardController(NoticeBoardService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllNoticeBoards(Model model) {
        model.addAttribute("noticeboards", service.getAllNoticeBoards());
        return "noticeboard/index"; // Calea: templates/noticeboard/index.html
    }

    @GetMapping("/new")
    public String showNewNoticeBoardForm(Model model) {
        model.addAttribute("noticeboard", new NoticeBoard());
        return "noticeboard/form"; // Calea: templates/noticeboard/form.html
    }

    @PostMapping
    public RedirectView addNoticeBoard(@ModelAttribute NoticeBoard noticeBoard) throws RepositoryException {
        // Service-ul se ocupă de generarea ID-ului și inițializarea listei goale de zboruri
        service.addNoticeBoard(noticeBoard);
        return new RedirectView("/noticeboards");
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteNoticeBoard(@PathVariable String id) throws RepositoryException {
        service.deleteNoticeBoard(id);
        return new RedirectView("/noticeboards");
    }

    /*
     * ########## METODE NOI PENTRU TEMA 3 ##########
     */

    @GetMapping("/{id}")
    public String getNoticeBoardDetails(@PathVariable String id, Model model) {
        try {
            model.addAttribute("noticeboard", service.getNoticeBoardById(id));
            return "noticeboard/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/noticeboards";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditNoticeBoardForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("noticeboard", service.getNoticeBoardById(id));
            return "noticeboard/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/noticeboards";
        }
    }

    @PostMapping("/{id}/edit")
    public RedirectView updateNoticeBoard(@PathVariable String id, @ModelAttribute NoticeBoard noticeBoard) {
        try {
            service.updateNoticeBoard(id, noticeBoard);
        } catch (RepositoryException e) {
            // Logare
        }
        return new RedirectView("/noticeboards");
    }

}