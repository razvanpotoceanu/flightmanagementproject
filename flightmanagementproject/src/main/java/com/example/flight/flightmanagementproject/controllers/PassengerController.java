package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
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

    // 1. LIST (CU SORTARE ȘI FILTRARE - Proiect 5)
    @GetMapping
    public String list(
            Model model,
            // Preluăm parametrii din URL (ex: ?keyword=Ion&sortField=email&sortDir=desc)
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        // Apelăm service-ul cu parametrii de sortare/filtrare
        model.addAttribute("passengers", service.getAllPassengers(keyword, sortField, sortDir));

        // Trimitem parametrii înapoi la HTML pentru a păstra starea formularului și a link-urilor
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        // Calculăm direcția inversă pentru link-urile de pe capul de tabel (click pentru a schimba ordinea)
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "passenger/index";
    }

    // 2. FORM NEW
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "passenger/form";
    }

    // 3. CREATE
    @PostMapping
    public String addPassenger(@Valid @ModelAttribute Passenger passenger, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "passenger/form";
        }
        try {
            service.savePassenger(passenger);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.passenger", e.getMessage());
            return "passenger/form";
        }
        return "redirect:/passengers";
    }

    // 4. FORM EDIT
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("passenger", service.getPassengerById(id));
            return "passenger/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/passengers";
        }
    }

    // 5. UPDATE
    @PostMapping("/{id}/edit")
    public String updatePassenger(@PathVariable Long id, @Valid @ModelAttribute Passenger passenger, BindingResult result, Model model) {
        if (result.hasErrors()) {
            passenger.setId(id);
            return "passenger/edit-form";
        }
        try {
            service.updatePassenger(id, passenger);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.passenger", e.getMessage());
            passenger.setId(id);
            return "passenger/edit-form";
        }
        return "redirect:/passengers";
    }

    // 6. DELETE
    @PostMapping("/{id}/delete")
    public String deletePassenger(@PathVariable Long id) {
        service.deletePassenger(id);
        return "redirect:/passengers";
    }

    // 7. DETAILS
    @GetMapping("/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("passenger", service.getPassengerById(id));
            return "passenger/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/passengers";
        }
    }
}