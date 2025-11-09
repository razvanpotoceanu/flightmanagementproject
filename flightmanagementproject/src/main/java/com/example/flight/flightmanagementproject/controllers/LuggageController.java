package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Luggage;
import com.example.flight.flightmanagementproject.services.LuggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/luggage") // Ruta de bază
public class LuggageController {

    private final LuggageService service;

    @Autowired
    public LuggageController(LuggageService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllLuggage(Model model) {
        model.addAttribute("luggages", service.getAllLuggages());
        return "luggage/index"; // Calea: templates/luggage/index.html
    }

    @GetMapping("/new")
    public String showNewLuggageForm(Model model) {
        model.addAttribute("luggage", new Luggage());
        return "luggage/form"; // Calea: templates/luggage/form.html
    }

    @PostMapping
    public RedirectView addLuggage(@ModelAttribute Luggage luggage) throws RepositoryException {
        service.addLuggage(luggage);
        return new RedirectView("/luggage");
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteLuggage(@PathVariable String id) throws RepositoryException {
        service.deleteLuggage(id);
        return new RedirectView("/luggage");
    }
}