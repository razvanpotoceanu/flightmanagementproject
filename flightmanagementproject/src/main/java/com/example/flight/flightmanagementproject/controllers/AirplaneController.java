package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Airplane;
import com.example.flight.flightmanagementproject.services.AirplaneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService service;

    @Autowired
    public AirplaneController(AirplaneService service) {
        this.service = service;
    }

    // 1. LIST: Afișează lista de avioane
    @GetMapping
    public String list(Model model) {
        model.addAttribute("airplanes", service.getAllAirplanes());
        return "airplane/index";
    }

    // 2. CREATE FORM: Afișează formularul de adăugare
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("airplane", new Airplane());
        return "airplane/form";
    }

    // 3. CREATE ACTION: Salvează avionul nou (cu validare)
    @PostMapping
    public String addAirplane(@Valid @ModelAttribute Airplane airplane, BindingResult result) {
        // Dacă există erori de validare (ex: model gol, capacitate < 10), rămânem pe formular
        if (result.hasErrors()) {
            return "airplane/form";
        }
        service.saveAirplane(airplane);
        return "redirect:/airplanes";
    }

    // 4. EDIT FORM: Afișează formularul de editare
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("airplane", service.getAirplaneById(id));
            return "airplane/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airplanes";
        }
    }

    // 5. UPDATE ACTION: Actualizează avionul existent (cu validare)
    @PostMapping("/{id}/edit")
    public String updateAirplane(@PathVariable Long id, @Valid @ModelAttribute Airplane airplane, BindingResult result) {
        if (result.hasErrors()) {
            airplane.setId(id); // Păstrăm ID-ul pentru a nu pierde contextul în formular
            return "airplane/edit-form";
        }
        service.updateAirplane(id, airplane);
        return "redirect:/airplanes";
    }

    // 6. DELETE ACTION: Șterge avionul
    @PostMapping("/{id}/delete")
    public String deleteAirplane(@PathVariable Long id) {
        service.deleteAirplane(id);
        return "redirect:/airplanes";
    }

    // 7. DETAILS: Afișează pagina de detalii
    @GetMapping("/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("airplane", service.getAirplaneById(id));
            return "airplane/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airplanes";
        }
    }
}