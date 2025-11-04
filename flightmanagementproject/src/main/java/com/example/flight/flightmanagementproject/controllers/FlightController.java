package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService service;

    @Autowired
    public FlightController(FlightService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllFlights(Model model) {
        model.addAttribute("flights", service.getAllFlights());
        return "flight/index";
    }

    @GetMapping("/new")
    public String showNewFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        return "flight/form";
    }

    // 3. POST CREATE: Am eliminat try...catch
    @PostMapping
    public RedirectView addFlight(@ModelAttribute Flight flight) throws RepositoryException {
        // Dacă service.addFlight eșuează, vei vedea acum eroarea 500
        service.addFlight(flight);

        return new RedirectView("/flights");
    }

    // 4. POST DELETE: Am eliminat try...catch
    @PostMapping("/{id}/delete")
    public RedirectView deleteFlight(@PathVariable String id) throws RepositoryException {
        service.deleteFlight(id);

        return new RedirectView("/flights");
    }
}