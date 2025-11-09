package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Luggage;
import com.example.flight.flightmanagementproject.enums.LuggageStatus; // Asigură-te că importul este din 'enums'
import com.example.flight.flightmanagementproject.services.LuggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/luggage")
public class LuggageController {

    private final LuggageService service;

    @Autowired
    public LuggageController(LuggageService service) {
        this.service = service;
    }

    /**
     * 1. GET ALL (Afișează lista)
     * Ruta: GET /luggage
     */
    @GetMapping
    public String getAllLuggage(Model model) {
        model.addAttribute("luggages", service.getAllLuggages());
        return "luggage/index";
    }

    /**
     * 2. GET NEW (Afișează formularul de creare)
     * Ruta: GET /luggage/new
     */
    @GetMapping("/new")
    public String showNewLuggageForm(Model model) {
        model.addAttribute("luggage", new Luggage());
        // Trimitem lista de statusuri (Enum) către formular
        model.addAttribute("statuses", LuggageStatus.values());
        return "luggage/form";
    }

    /**
     * 3. POST CREATE (Procesează formularul)
     * Ruta: POST /luggage
     */
    @PostMapping
    public RedirectView addLuggage(@ModelAttribute Luggage luggage) throws RepositoryException {
        service.addLuggage(luggage);
        return new RedirectView("/luggage");
    }

    /**
     * 4. POST DELETE (Șterge)
     * Ruta: POST /luggage/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public RedirectView deleteLuggage(@PathVariable String id) throws RepositoryException {
        service.deleteLuggage(id);
        return new RedirectView("/luggage");
    }

    /**
     * 5. GET DETAILS (Afișează detalii)
     * Ruta: GET /luggage/{id}
     */
    @GetMapping("/{id}")
    public String getLuggageDetails(@PathVariable String id, Model model) {
        try {
            model.addAttribute("luggage", service.getLuggageById(id));
            return "luggage/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/luggage";
        }
    }

    /**
     * 6. GET EDIT (Afișează formularul de editare)
     * Ruta: GET /luggage/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String showEditLuggageForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("luggage", service.getLuggageById(id));
            // Trimitem lista de statusuri (Enum) și la formularul de editare
            model.addAttribute("statuses", LuggageStatus.values());
            return "luggage/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/luggage";
        }
    }

    /**
     * 7. POST UPDATE (Procesează formularul de editare)
     * Ruta: POST /luggage/{id}/edit
     */
    @PostMapping("/{id}/edit")
    public RedirectView updateLuggage(@PathVariable String id, @ModelAttribute Luggage luggage) throws RepositoryException {
        service.updateLuggage(id, luggage);
        return new RedirectView("/luggage");
    }
}