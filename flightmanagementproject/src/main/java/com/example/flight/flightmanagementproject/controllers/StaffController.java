package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Staff; // Folosești clasa de bază
import com.example.flight.flightmanagementproject.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/staff") // Ruta de bază: /staff
public class StaffController {

    private final StaffService service;

    @Autowired
    public StaffController(StaffService service) {
        this.service = service;
    }

    // 1. GET ALL: Afișează lista
    @GetMapping
    public String getAllStaff(Model model) {
        model.addAttribute("staff", service.getAllStaff());
        return "staff/index";
    }

    // 2. GET NEW: Afișează formularul de creare (Aici va fi simplificat)
    @GetMapping("/new")
    public String showNewStaffForm(Model model) {
        // Pentru simplitate, returnăm un obiect Staff de bază
        model.addAttribute("staff", new Staff());
        // Dacă ai mai multe formulare (ex: /new/pilot, /new/steward),
        // trebuie să tratezi rute separate.
        return "staff/form";
    }

    // 3. POST CREATE: Procesează formularul și salvează
    // Aici Spring Jackson se va ocupa de maparea pe subtip (AirlineEmployee/AirportEmployee)
    @PostMapping
    public RedirectView addStaff(@ModelAttribute Staff staff) {
        try {
            service.addStaff(staff);
        } catch (Exception e) {
            // Logare simplă
        }
        return new RedirectView("/staff");
    }

    // 4. POST DELETE: Șterge un membru Staff după ID
    @PostMapping("/{id}/delete")
    public RedirectView deleteStaff(@PathVariable String id) {
        try {
            service.deleteStaff(id);
        } catch (ResourceNotFoundException e) {
            // Logare simplă
        }
        return new RedirectView("/staff");
    }
}