package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.services.AirplaneService;
import com.example.flight.flightmanagementproject.services.FlightService;
import com.example.flight.flightmanagementproject.services.NoticeBoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final AirplaneService airplaneService;
    private final NoticeBoardService noticeBoardService;

    @Autowired
    public FlightController(FlightService flightService, AirplaneService airplaneService, NoticeBoardService noticeBoardService) {
        this.flightService = flightService;
        this.airplaneService = airplaneService;
        this.noticeBoardService = noticeBoardService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "flight/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("flight", new Flight());
        populateDropdowns(model);
        return "flight/form";
    }

    @PostMapping
    public String addFlight(@Valid @ModelAttribute Flight flight, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            return "flight/form";
        }

        try {
            flightService.saveFlight(flight);
        } catch (IllegalArgumentException e) {
            handleBusinessException(e, result);
            populateDropdowns(model);
            return "flight/form";
        }

        return "redirect:/flights";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("flight", flightService.getFlightById(id));
            populateDropdowns(model);
            return "flight/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/flights";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateFlight(@PathVariable Long id, @Valid @ModelAttribute Flight flight, BindingResult result, Model model) {
        if (result.hasErrors()) {
            flight.setId(id);
            populateDropdowns(model);
            return "flight/edit-form";
        }

        try {
            flightService.updateFlight(id, flight);
        } catch (IllegalArgumentException e) {
            handleBusinessException(e, result);
            flight.setId(id);
            populateDropdowns(model);
            return "flight/edit-form";
        }

        return "redirect:/flights";
    }

    @PostMapping("/{id}/delete")
    public String deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return "redirect:/flights";
    }

    @GetMapping("/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("flight", flightService.getFlightById(id));
            return "flight/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/flights";
        }
    }

    // Metodă helper pentru a popula dropdown-urile (DRY)
    private void populateDropdowns(Model model) {
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
    }

    // Metodă helper pentru a mapa mesajele de eroare la câmpurile corecte
    private void handleBusinessException(IllegalArgumentException e, BindingResult result) {
        String msg = e.getMessage();
        if (msg.contains("numărul")) {
            result.rejectValue("flightNumber", "error.flight", msg);
        } else if (msg.contains("Avionul")) {
            result.rejectValue("airplane", "error.flight", msg);
        } else if (msg.contains("ora") || msg.contains("Format")) {
            // Dacă eroarea e legată de timp, o punem la departureTime (sau arrivalTime)
            // sau global, depinde. O punem la departure pentru vizibilitate.
            result.rejectValue("departureTime", "error.flight", msg);
            // O putem pune și global:
            // result.reject("error.flight", msg);
        } else {
            // Eroare generică
            result.reject("error.flight", msg);
        }
    }
}