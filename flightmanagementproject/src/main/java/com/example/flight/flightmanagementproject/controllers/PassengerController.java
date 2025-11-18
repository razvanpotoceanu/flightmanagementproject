package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.models.Passenger;
import com.example.flight.flightmanagementproject.services.PassengerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService service;

    @Autowired
    public PassengerController(PassengerService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("passengers", service.getAllPassengers());
        return "passenger/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "passenger/form";
    }

    @PostMapping
    // @Valid declanșează validarea. BindingResult colectează erorile.
    public String addPassenger(@Valid @ModelAttribute Passenger passenger, BindingResult result) {
        if (result.hasErrors()) {
            // Dacă sunt erori de validare, reafișăm formularul (cu erori)
            return "passenger/form";
        }
        service.savePassenger(passenger);
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("passenger", service.getPassengerById(id));
        return "passenger/edit-form";
    }

    @PostMapping("/{id}/edit")
    public String updatePassenger(@PathVariable Long id, @Valid @ModelAttribute Passenger passenger, BindingResult result) {
        if (result.hasErrors()) {
            return "passenger/edit-form";
        }
        service.updatePassenger(id, passenger);
        return "redirect:/passengers";
    }

    @PostMapping("/{id}/delete")
    public String deletePassenger(@PathVariable Long id) {
        service.deletePassenger(id);
        return "redirect:/passengers";
    }
}