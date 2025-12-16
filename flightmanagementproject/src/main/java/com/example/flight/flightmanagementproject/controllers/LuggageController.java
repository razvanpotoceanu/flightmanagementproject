package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.enums.LuggageStatus;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Luggage;
import com.example.flight.flightmanagementproject.services.LuggageService;
import com.example.flight.flightmanagementproject.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/luggage")
public class LuggageController {

    private final LuggageService service;
    private final TicketService ticketService;

    @Autowired
    public LuggageController(LuggageService service, TicketService ticketService) {
        this.service = service;
        this.ticketService = ticketService;
    }

    // 1. LISTARE (Cu Sortare și Filtrare)
    @GetMapping
    public String list(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        model.addAttribute("luggages", service.getAllLuggages(keyword, sortField, sortDir));

        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "luggage/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("luggage", new Luggage());
        populateDropdowns(model);
        return "luggage/form";
    }

    @PostMapping
    public String addLuggage(@Valid @ModelAttribute Luggage luggage, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            return "luggage/form";
        }
        try {
            service.saveLuggage(luggage);
        } catch (IllegalArgumentException e) {
            result.rejectValue("ticket", "error.luggage", e.getMessage());
            populateDropdowns(model);
            return "luggage/form";
        }
        return "redirect:/luggage";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("luggage", service.getLuggageById(id));
            populateDropdowns(model);
            return "luggage/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/luggage";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateLuggage(@PathVariable Long id, @Valid @ModelAttribute Luggage luggage, BindingResult result, Model model) {
        if (result.hasErrors()) {
            luggage.setId(id);
            populateDropdowns(model);
            return "luggage/edit-form";
        }
        try {
            service.updateLuggage(id, luggage);
        } catch (IllegalArgumentException e) {
            result.rejectValue("ticket", "error.luggage", e.getMessage());
            luggage.setId(id);
            populateDropdowns(model);
            return "luggage/edit-form";
        }
        return "redirect:/luggage";
    }

    @PostMapping("/{id}/delete")
    public String deleteLuggage(@PathVariable Long id) {
        service.deleteLuggage(id);
        return "redirect:/luggage";
    }

    @GetMapping("/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("luggage", service.getLuggageById(id));
            return "luggage/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/luggage";
        }
    }

    private void populateDropdowns(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        model.addAttribute("statuses", LuggageStatus.values());
    }
}