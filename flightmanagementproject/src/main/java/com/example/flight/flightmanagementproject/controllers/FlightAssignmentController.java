package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.FlightAssignment;
import com.example.flight.flightmanagementproject.services.FlightAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller // NECESAR: Marchează clasa ca un Controller
@RequestMapping("/assignments") // NECESAR: Definește ruta publică
public class FlightAssignmentController {

    private final FlightAssignmentService service;

    @Autowired // NECESAR: Injectează Service-ul
    public FlightAssignmentController(FlightAssignmentService service) {
        this.service = service;
    }

    // GET ALL: Afișează lista - Răspunde la GET /assignments
    @GetMapping
    public String getAllAssignments(Model model) {
        model.addAttribute("assignments", service.getAllFlightAssignments());
        return "assignment/index";
    }

    // GET NEW: Afișează formularul - Răspunde la GET /assignments/new
    @GetMapping("/new")
    public String showNewAssignmentForm(Model model) {
        model.addAttribute("assignment", new FlightAssignment());
        return "assignment/form";
    }

    // POST CREATE: Salvează - Răspunde la POST /assignments
    @PostMapping
    public RedirectView addAssignment(@ModelAttribute FlightAssignment assignment) throws RepositoryException {
        // Logica de generare ID este în Service (sperăm)
        service.addFlightAssignment(assignment);
        return new RedirectView("/assignments");
    }

    // POST DELETE: Șterge - Răspunde la POST /assignments/{id}/delete
    @PostMapping("/{id}/delete")
    public RedirectView deleteAssignment(@PathVariable String id) throws RepositoryException {
        service.deleteFlightAssignment(id);
        return new RedirectView("/assignments");
    }
    /*
     * ########## METODE NOI PENTRU TEMA 3 ##########
     */

    // GET DETAILS
    @GetMapping("/{id}")
    public String getAssignmentDetails(@PathVariable String id, Model model) {
        try {
            model.addAttribute("assignment", service.getFlightAssignmentById(id));
            return "assignment/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/assignments";
        }
    }

    // GET EDIT
    @GetMapping("/{id}/edit")
    public String showEditAssignmentForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("assignment", service.getFlightAssignmentById(id));
            return "assignment/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/assignments";
        }
    }

    // POST UPDATE
    @PostMapping("/{id}/edit")
    public RedirectView updateAssignment(@PathVariable String id, @ModelAttribute FlightAssignment assignment) {
        try {
            service.updateFlightAssignment(id, assignment);
        } catch (RepositoryException e) {
            // Logare
        }
        return new RedirectView("/assignments");
    }

}