package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.enums.AirlineEmployeeRole;
import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.AirlineEmployee;
import com.example.flight.flightmanagementproject.services.AirlineEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/airline-employees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService service;

    @Autowired
    public AirlineEmployeeController(AirlineEmployeeService service) {
        this.service = service;
    }

    // 1. LISTA
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("employees", service.getAll());
        return "airline-employee/index";
    }

    // 2. FORMULAR CREARE
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("employee", new AirlineEmployee());
        model.addAttribute("roles", AirlineEmployeeRole.values());
        return "airline-employee/form";
    }

    // 3. SALVARE
    @PostMapping
    public RedirectView add(@ModelAttribute AirlineEmployee employee) throws RepositoryException {
        service.add(employee);
        return new RedirectView("/airline-employees");
    }

    // 4. DETALII (Aici era posibil problema)
    @GetMapping("/{id}")
    public String getDetails(@PathVariable String id, Model model) {
        try {
            model.addAttribute("employee", service.getById(id));
            return "airline-employee/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airline-employees";
        }
    }

    // 5. FORMULAR EDITARE (Aici era posibil problema)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("employee", service.getById(id));
            model.addAttribute("roles", AirlineEmployeeRole.values());
            return "airline-employee/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airline-employees";
        }
    }

    // 6. UPDATE
    @PostMapping("/{id}/edit")
    public RedirectView update(@PathVariable String id, @ModelAttribute AirlineEmployee employee) throws RepositoryException {
        service.update(id, employee);
        return new RedirectView("/airline-employees");
    }

    // 7. DELETE
    @PostMapping("/{id}/delete")
    public RedirectView delete(@PathVariable String id) throws RepositoryException {
        service.delete(id);
        return new RedirectView("/airline-employees");
    }
}