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

    // 1. LISTARE (Cu Sortare și Filtrare - Proiect 5)
    @GetMapping
    public String list(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        model.addAttribute("employees", service.getAll(keyword, sortField, sortDir));
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "airport-employee/index";
    }

    // 2. FORMULAR CREARE
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new AirportEmployee());
        return "airport-employee/form";
    }

    // 3. SALVARE
    @PostMapping
    public String add(@Valid @ModelAttribute("employee") AirportEmployee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "airport-employee/form";
        }

        try {
            service.save(employee);
        } catch (IllegalArgumentException e) {
            result.rejectValue("name", "error.employee", e.getMessage());
            return "airport-employee/form";
        }

        return "redirect:/airport-employees";
    }

    // 4. FORMULAR EDITARE
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("employee", service.getById(id));
            return "airport-employee/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/airport-employees";
        }
    }

    // 5. UPDATE
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("employee") AirportEmployee employee, BindingResult result) {
        if (result.hasErrors()) {
            employee.setId(id);
            return "airport-employee/edit-form";
        }

        try {
            service.update(id, employee);
        } catch (IllegalArgumentException e) {
            result.rejectValue("name", "error.employee", e.getMessage());
            employee.setId(id);
            return "airport-employee/edit-form";
        }

        return "redirect:/airport-employees";
    }

    // 6. ȘTERGERE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/airport-employees";
    }

    // 7. DETALII
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