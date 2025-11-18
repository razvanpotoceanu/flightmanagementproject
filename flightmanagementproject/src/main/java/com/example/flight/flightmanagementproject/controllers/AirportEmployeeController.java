package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirportEmployee;
import com.example.flight.flightmanagementproject.services.AirportEmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airport-employees")
public class AirportEmployeeController {

    private final AirportEmployeeService service;

    @Autowired
    public AirportEmployeeController(AirportEmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", service.getAll());
        return "airport-employee/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new AirportEmployee());
        return "airport-employee/form";
    }

    @PostMapping
    public String add(@Valid @ModelAttribute("employee") AirportEmployee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "airport-employee/form";
        }
        service.save(employee);
        return "redirect:/airport-employees";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("employee", service.getById(id));
            return "airport-employee/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airport-employees";
        }
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("employee") AirportEmployee employee, BindingResult result) {
        if (result.hasErrors()) {
            employee.setId(id);
            return "airport-employee/edit-form";
        }
        service.update(id, employee);
        return "redirect:/airport-employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/airport-employees";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("employee", service.getById(id));
            return "airport-employee/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airport-employees";
        }
    }
}