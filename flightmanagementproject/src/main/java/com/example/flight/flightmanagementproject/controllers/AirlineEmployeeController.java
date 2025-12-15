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

    // 1. LISTARE (Cu Sortare și Filtrare - Proiect 5)
    @GetMapping
    public String list(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        // Apelăm service-ul cu parametrii de căutare și sortare
        model.addAttribute("employees", service.getAll(keyword, sortField, sortDir));

        // Trimitem parametrii înapoi la View
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "airline-employee/index";
    }

    // 2. FORMULAR CREARE
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new AirlineEmployee());
        model.addAttribute("roles", AirlineEmployeeRole.values());
        return "airline-employee/form";
    }

    // 3. SALVARE
    @PostMapping
    public String add(@Valid @ModelAttribute("employee") AirlineEmployee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", AirlineEmployeeRole.values());
            return "airline-employee/form";
        }

        try {
            service.save(employee);
        } catch (IllegalArgumentException e) {
            result.rejectValue("name", "error.employee", e.getMessage());
            model.addAttribute("roles", AirlineEmployeeRole.values());
            return "airline-employee/form";
        }

        return "redirect:/airline-employees";
    }

    // 4. FORMULAR EDITARE
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

    // 5. UPDATE
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("employee") AirlineEmployee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            employee.setId(id);
            model.addAttribute("roles", AirlineEmployeeRole.values());
            return "airline-employee/edit-form";
        }

        try {
            service.update(id, employee);
        } catch (IllegalArgumentException e) {
            result.rejectValue("name", "error.employee", e.getMessage());
            employee.setId(id);
            model.addAttribute("roles", AirlineEmployeeRole.values());
            return "airline-employee/edit-form";
        }

        return "redirect:/airline-employees";
    }

    // 6. ȘTERGERE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/airline-employees";
    }

    // 7. DETALII
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