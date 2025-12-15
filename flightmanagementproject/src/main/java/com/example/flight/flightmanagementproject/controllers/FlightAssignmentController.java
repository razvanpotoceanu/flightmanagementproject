package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
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

    // 1. LISTARE (Cu Sortare și Filtrare - Proiect 5)
    @GetMapping
    public String list(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        // Apelăm service-ul actualizat care suportă sortarea și filtrarea
        model.addAttribute("assignments", service.getAllFlightAssignments(keyword, sortField, sortDir));

        // Trimitem parametrii înapoi la View pentru a păstra starea
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "assignment/index";
    }

    // 2. FORMULAR CREARE
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("assignment", new FlightAssignment());
        populateModelLists(model); // Populăm dropdown-urile
        return "assignment/form";
    }

    // 3. SALVARE
    @PostMapping
    public String addAssignment(@Valid @ModelAttribute FlightAssignment assignment, BindingResult result, Model model) {
        // Validare standard
        if (result.hasErrors()) {
            populateModelLists(model);
            return "assignment/form";
        }

        // Validare Business (prindem excepțiile din Service, ex: "Nu poți selecta ambii angajați")
        try {
            service.saveFlightAssignment(assignment);
        } catch (IllegalArgumentException e) {
            // Afișăm eroarea pe un câmp pentru a fi vizibilă în formular
            // Putem alege să o punem pe 'airlineEmployee' sau global
            result.rejectValue("airlineEmployee", "error.assignment", e.getMessage());
            populateModelLists(model);
            return "assignment/form";
        }

        return "redirect:/assignments";
    }

    // 4. FORMULAR EDITARE
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("assignment", service.getFlightAssignmentById(id));
            populateModelLists(model);
            return "assignment/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/assignments";
        }
    }

    // 5. UPDATE
    @PostMapping("/{id}/edit")
    public String updateAssignment(@PathVariable Long id, @Valid @ModelAttribute FlightAssignment assignment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            assignment.setId(id);
            populateModelLists(model);
            return "assignment/edit-form";
        }

        try {
            service.updateFlightAssignment(id, assignment);
        } catch (IllegalArgumentException e) {
            result.rejectValue("airlineEmployee", "error.assignment", e.getMessage());
            assignment.setId(id);
            populateModelLists(model);
            return "assignment/edit-form";
        }

        return "redirect:/assignments";
    }

    // 6. ȘTERGERE
    @PostMapping("/{id}/delete")
    public String deleteAssignment(@PathVariable Long id) {
        service.deleteFlightAssignment(id);
        return "redirect:/assignments";
    }

    // 7. DETALII
    @GetMapping("/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("assignment", service.getFlightAssignmentById(id));
            return "assignment/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/assignments";
        }
    }

    // Metodă privată pentru a popula listele dropdown
    // Folosim metodele findAll() din celelalte servicii
    // (Asumăm că celelalte servicii au o metodă getAll() fără parametri sau că getAll(null, "id", "asc") funcționează)
    private void populateModelLists(Model model) {
        // Dacă ai șters metoda fără parametri din celelalte servicii, folosește varianta cu parametri null:
        // flightService.getAllFlights(null, "id", "asc");
        // Dar pentru curățenie, am presupus că există metoda getAll() simplă.
        model.addAttribute("flights", flightService.getAllFlights());
        model.addAttribute("airlineEmployees", airlineService.getAll());
        model.addAttribute("airportEmployees", airportService.getAll());
    }
}