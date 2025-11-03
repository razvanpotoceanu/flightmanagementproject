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

    // 3a. POST CREATE (pentru AirlineEmployee)
    @PostMapping("/airline")
    public RedirectView addAirlineEmployee(@ModelAttribute AirlineEmployee airlineEmployee)
            throws RepositoryException { // Adaugă "throws" dacă e necesar

        // Acum, dacă service.addStaff eșuează, vei primi eroarea 500
        service.addStaff(airlineEmployee);

        return new RedirectView("/staff");
    }

    // 3b. POST CREATE (pentru AirportEmployee)
    @PostMapping("/airport") // Rută nouă
    public RedirectView addAirportEmployee(@ModelAttribute AirportEmployee airportEmployee) {
        try {
            service.addStaff(airportEmployee);
        } catch (Exception e) {
            // Logare
        }
        return new RedirectView("/staff");
    }

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