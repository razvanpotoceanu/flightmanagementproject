package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirlineEmployee; // Importăm subtipul
import com.example.flight.flightmanagementproject.models.AirportEmployee; // Importăm subtipul
import com.example.flight.flightmanagementproject.models.Staff;
import com.example.flight.flightmanagementproject.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffService service;

    @Autowired
    public StaffController(StaffService service) {
        this.service = service;
    }

    // 1. GET ALL (Rămâne la fel)
    @GetMapping
    public String getAllStaff(Model model) {
        model.addAttribute("staff", service.getAllStaff());
        return "staff/index";
    }

    // --- SCHIMBARE PENTRU CREARE ---

    // 2a. GET NEW (pentru AirlineEmployee)
    @GetMapping("/new/airline")
    public String showNewAirlineForm(Model model) {
        model.addAttribute("airlineEmployee", new AirlineEmployee()); // Trimitem un obiect specific
        return "staff/form-airline"; // Folosim un template nou
    }

    // 2b. GET NEW (pentru AirportEmployee)
    @GetMapping("/new/airport")
    public String showNewAirportForm(Model model) {
        model.addAttribute("airportEmployee", new AirportEmployee()); // Trimitem un obiect specific
        return "staff/form-airport"; // Folosim un template nou
    }

    // ... (restul controller-ului) ...

    // 3a. POST CREATE (pentru AirlineEmployee) - (Probabil deja corectat)
    @PostMapping("/airline")
    public RedirectView addAirlineEmployee(@ModelAttribute AirlineEmployee airlineEmployee) throws RepositoryException {
        service.addStaff(airlineEmployee);
        return new RedirectView("/staff");
    }

    // 3b. POST CREATE (pentru AirportEmployee) - (MODIFICAT AICI)
    @PostMapping("/airport")
    public RedirectView addAirportEmployee(@ModelAttribute AirportEmployee airportEmployee) throws RepositoryException {
        // Am scos blocul try...catch.
        // Dacă 'addStaff' eșuează, vei vedea eroarea 500 în consolă.
        service.addStaff(airportEmployee);

        return new RedirectView("/staff");
    }

// ... (restul controller-ului) ...

    // 4. POST DELETE (Rămâne la fel)
    @PostMapping("/{id}/delete")
    public RedirectView deleteStaff(@PathVariable String id) {
        try {
            service.deleteStaff(id);
        } catch (ResourceNotFoundException e) {
            // Logare
        }
        return new RedirectView("/staff");
    }
}