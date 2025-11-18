package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirportEmployee;
import com.example.flight.flightmanagementproject.services.AirportEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/airport-employees")
public class AirportEmployeeController {

    private final AirportEmployeeService service;

    @Autowired
    public AirportEmployeeController(AirportEmployeeService service) {
        this.service = service;
    }

    // 1. LISTA
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("employees", service.getAll());
        return "airport-employee/index";
    }

    // 2. FORMULAR CREARE
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("employee", new AirportEmployee());
        return "airport-employee/form";
    }

    // 3. SALVARE
    @PostMapping
    public RedirectView add(@ModelAttribute AirportEmployee employee) throws RepositoryException {
        service.add(employee);
        return new RedirectView("/airport-employees");
    }

    // 4. DETALII
    @GetMapping("/{id}")
    public String getDetails(@PathVariable String id, Model model) {
        try {
            model.addAttribute("employee", service.getById(id));
            return "airport-employee/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airport-employees";
        }
    }

    // 5. FORMULAR EDITARE
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("employee", service.getById(id));
            return "airport-employee/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airport-employees";
        }
    }

    // 6. UPDATE
    @PostMapping("/{id}/edit")
    public RedirectView update(@PathVariable String id, @ModelAttribute AirportEmployee employee) throws RepositoryException {
        service.update(id, employee);
        return new RedirectView("/airport-employees");
    }

    // 7. DELETE
    @PostMapping("/{id}/delete")
    public RedirectView delete(@PathVariable String id) throws RepositoryException {
        service.delete(id);
        return new RedirectView("/airport-employees");
    }
}