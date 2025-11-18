package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.enums.AirlineEmployeeRole;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import com.example.flight.flightmanagementproject.services.AirlineEmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airline-employees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService service;

    @Autowired
    public AirlineEmployeeController(AirlineEmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", service.getAll());
        return "airline-employee/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new AirlineEmployee());
        model.addAttribute("roles", AirlineEmployeeRole.values());
        return "airline-employee/form";
    }

    @PostMapping
    public String add(@Valid @ModelAttribute("employee") AirlineEmployee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", AirlineEmployeeRole.values());
            return "airline-employee/form";
        }
        service.save(employee);
        return "redirect:/airline-employees";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("employee", service.getById(id));
            model.addAttribute("roles", AirlineEmployeeRole.values());
            return "airline-employee/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airline-employees";
        }
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("employee") AirlineEmployee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", AirlineEmployeeRole.values());
            employee.setId(id);
            return "airline-employee/edit-form";
        }
        service.update(id, employee);
        return "redirect:/airline-employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/airline-employees";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("employee", service.getById(id));
            return "airline-employee/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airline-employees";
        }
    }
}