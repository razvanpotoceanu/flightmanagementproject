package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.enums.EmployeeType;
import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Staff;
import com.example.flight.flightmanagementproject.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffService service;

    @Autowired
    public StaffController(StaffService service) {
        this.service = service;
    }

    // 1. GET ALL
    @GetMapping
    public String getAllStaff(Model model) {
        model.addAttribute("staff", service.getAllStaff());
        return "staff/index";
    }

    // 2. GET NEW (O singură rută)
    @GetMapping("/new")
    public String showNewStaffForm(Model model) {
        model.addAttribute("staff", new Staff());
        // Trimitem lista de tipuri (Enum) către formular
        model.addAttribute("employeeTypes", EmployeeType.values());
        return "staff/form"; // Folosim noul formular unificat
    }

    // 3. POST CREATE (O singură rută)
    @PostMapping
    public RedirectView addStaff(@ModelAttribute Staff staff) throws RepositoryException {
        service.addStaff(staff);
        return new RedirectView("/staff");
    }

    // 4. POST DELETE (Rămâne la fel)
    @PostMapping("/{id}/delete")
    public RedirectView deleteStaff(@PathVariable String id) throws RepositoryException {
        service.deleteStaff(id);
        return new RedirectView("/staff");
    }

    // 5. GET DETAILS (Rămâne la fel, dar folosim noul template)
    @GetMapping("/{id}")
    public String getStaffDetails(@PathVariable String id, Model model) {
        try {
            model.addAttribute("staff", service.getStaffById(id));
            return "staff/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/staff";
        }
    }

    // 6. GET EDIT (O singură rută)
    @GetMapping("/{id}/edit")
    public String showEditStaffForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("staff", service.getStaffById(id));
            // Trimitem lista de tipuri (Enum) către formular
            model.addAttribute("employeeTypes", EmployeeType.values());
            return "staff/edit-form"; // Folosim noul formular unificat
        } catch (ResourceNotFoundException e) {
            return "redirect:/staff";
        }
    }

    // 7. POST UPDATE (O singură rută)
    @PostMapping("/{id}/edit")
    public RedirectView updateStaff(@PathVariable String id, @ModelAttribute Staff staff) {
        try {
            service.updateStaff(id, staff);
        } catch (RepositoryException e) {
            // Logare
        }
        return new RedirectView("/staff");
    }
}