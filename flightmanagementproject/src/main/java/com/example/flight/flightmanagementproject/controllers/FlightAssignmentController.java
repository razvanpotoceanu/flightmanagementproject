package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.models.FlightAssignment;
import com.example.flight.flightmanagementproject.services.AirlineEmployeeService;
import com.example.flight.flightmanagementproject.services.AirportEmployeeService;
import com.example.flight.flightmanagementproject.services.FlightAssignmentService;
import com.example.flight.flightmanagementproject.services.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignments")
public class FlightAssignmentController {

    private final FlightAssignmentService service;
    private final FlightService flightService;
    private final AirlineEmployeeService airlineService;
    private final AirportEmployeeService airportService;

    @Autowired
    public FlightAssignmentController(FlightAssignmentService service,
                                      FlightService flightService,
                                      AirlineEmployeeService airlineService,
                                      AirportEmployeeService airportService) {
        this.service = service;
        this.flightService = flightService;
        this.airlineService = airlineService;
        this.airportService = airportService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("assignments", service.getAllFlightAssignments());
        return "assignment/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("assignment", new FlightAssignment());
        populateModelLists(model); // Metodă ajutătoare pentru liste
        return "assignment/form";
    }

    @PostMapping
    public String addAssignment(@Valid @ModelAttribute FlightAssignment assignment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateModelLists(model);
            return "assignment/form";
        }
        service.saveFlightAssignment(assignment);
        return "redirect:/assignments";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("assignment", service.getFlightAssignmentById(id));
        populateModelLists(model);
        return "assignment/edit-form";
    }

    @PostMapping("/{id}/edit")
    public String updateAssignment(@PathVariable Long id, @Valid @ModelAttribute FlightAssignment assignment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            assignment.setId(id);
            populateModelLists(model);
            return "assignment/edit-form";
        }
        service.updateFlightAssignment(id, assignment);
        return "redirect:/assignments";
    }

    @PostMapping("/{id}/delete")
    public String deleteAssignment(@PathVariable Long id) {
        service.deleteFlightAssignment(id);
        return "redirect:/assignments";
    }

    // Metodă privată pentru a nu repeta codul de populare a listelor dropdown
    private void populateModelLists(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        model.addAttribute("airlineEmployees", airlineService.getAll());
        model.addAttribute("airportEmployees", airportService.getAll());
    }
}