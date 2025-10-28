package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/flights") // Ruta de bază: /flights
public class FlightController {

    private final FlightService service;

    @Autowired
    public FlightController(FlightService service) {
        this.service = service;
    }

    // 1. GET ALL: Afișează lista de zboruri (index.html)
    @GetMapping
    public String getAllFlights(Model model) {
        model.addAttribute("flights", service.getAllFlights());
        return "flight/index";
    }

    // 2. GET NEW: Afișează formularul de creare (form.html)
    @GetMapping("/new")
    public String showNewFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        return "flight/form";
    }

    // 3. POST CREATE: Procesează formularul și salvează
    @PostMapping
    public RedirectView addFlight(@ModelAttribute Flight flight) {
        try {
            service.addFlight(flight);
        } catch (Exception e) {
            // Logare simplă
        }
        return new RedirectView("/flights");
    }

    // 4. POST DELETE: Șterge un zbor după ID
    @PostMapping("/{id}/delete")
    public RedirectView deleteFlight(@PathVariable String id) {
        try {
            service.deleteFlight(id);
        } catch (ResourceNotFoundException e) {
            // Logare simplă
        }
        return new RedirectView("/flights");
    }
}