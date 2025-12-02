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
        // Trimitem listele pentru dropdown-uri
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
        return "flight/form";
    }

    @PostMapping
    public String addFlight(@Valid @ModelAttribute Flight flight, BindingResult result, Model model) {
        // 1. Validare de Format (ex: câmpuri goale)
        if (result.hasErrors()) {
            // Reîncărcăm listele în caz de eroare, altfel pagina crapă
            model.addAttribute("airplanes", airplaneService.getAllAirplanes());
            model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
            return "flight/form";
        }

        // 2. Validare de Business (ex: număr zbor duplicat)
        try {
            flightService.saveFlight(flight);
        } catch (IllegalArgumentException e) {
            // Adăugăm eroarea manual în BindingResult
            result.rejectValue("flightNumber", "error.flight", e.getMessage());
            // Reîncărcăm listele
            model.addAttribute("airplanes", airplaneService.getAllAirplanes());
            model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
            return "flight/form";
        }

        return "redirect:/flights";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("flight", flightService.getFlightById(id));
            model.addAttribute("airplanes", airplaneService.getAllAirplanes());
            model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
            return "flight/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/flights";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateFlight(@PathVariable Long id, @Valid @ModelAttribute Flight flight, BindingResult result, Model model) {
        if (result.hasErrors()) {
            flight.setId(id);
            model.addAttribute("airplanes", airplaneService.getAllAirplanes());
            model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
            return "flight/edit-form";
        }

        try {
            flightService.updateFlight(id, flight);
        } catch (IllegalArgumentException e) {
            result.rejectValue("flightNumber", "error.flight", e.getMessage());
            flight.setId(id);
            model.addAttribute("airplanes", airplaneService.getAllAirplanes());
            model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
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
}