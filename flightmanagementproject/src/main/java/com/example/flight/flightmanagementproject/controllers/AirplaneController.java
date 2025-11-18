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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("airplanes", service.getAllAirplanes());
        return "airplane/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("airplane", new Airplane());
        return "airplane/form";
    }

    @PostMapping
    public String addAirplane(@Valid @ModelAttribute Airplane airplane, BindingResult result) {
        if (result.hasErrors()) {
            return "airplane/form";
        }
        service.saveAirplane(airplane);
        return "redirect:/airplanes";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("airplane", service.getAirplaneById(id));
            return "airplane/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airplanes";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateAirplane(@PathVariable Long id, @Valid @ModelAttribute Airplane airplane, BindingResult result) {
        if (result.hasErrors()) {
            airplane.setId(id);
            return "airplane/edit-form";
        }
        service.updateAirplane(id, airplane);
        return "redirect:/airplanes";
    }

    @PostMapping("/{id}/delete")
    public String deleteAirplane(@PathVariable Long id) {
        service.deleteAirplane(id);
        return "redirect:/airplanes";
    }

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