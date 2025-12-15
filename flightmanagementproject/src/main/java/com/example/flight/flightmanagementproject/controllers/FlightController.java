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

    // 1. LIST (CU SORTARE ȘI FILTRARE)
    @GetMapping
    public String list(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        model.addAttribute("flights", flightService.getAllFlights(keyword, sortField, sortDir));
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

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

    // ... (restul metodelor edit/delete rămân la fel, doar asigură-te că au validarea de mai sus)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("flight", flightService.getFlightById(id));
            populateDropdowns(model);
            return "flight/edit-form";
        } catch (ResourceNotFoundException e) { return "redirect:/flights"; }
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
        } catch (ResourceNotFoundException e) { return "redirect:/flights"; }
    }

    private void populateDropdowns(Model model) {
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
    }

    private void handleBusinessException(IllegalArgumentException e, BindingResult result) {
        String msg = e.getMessage();
        if (msg.contains("numărul")) result.rejectValue("flightNumber", "error.flight", msg);
        else if (msg.contains("ora") || msg.contains("Format")) result.rejectValue("departureTime", "error.flight", msg);
        else if (msg.contains("Avionul")) result.rejectValue("airplane", "error.flight", msg);
        else result.reject("error.flight", msg);
    }
}