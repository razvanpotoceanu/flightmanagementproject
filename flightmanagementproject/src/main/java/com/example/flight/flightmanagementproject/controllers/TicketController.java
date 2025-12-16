package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Ticket;
import com.example.flight.flightmanagementproject.services.FlightService;
import com.example.flight.flightmanagementproject.services.PassengerService;
import com.example.flight.flightmanagementproject.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final PassengerService passengerService;
    private final FlightService flightService;

    @Autowired
    public TicketController(TicketService ticketService, PassengerService passengerService, FlightService flightService) {
        this.ticketService = ticketService;
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    // 1. LISTARE (Cu Sortare și Filtrare - Proiect 5)
    @GetMapping
    public String list(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        // Apelăm metoda nouă din Service care acceptă parametri
        model.addAttribute("tickets", ticketService.getAllTickets(keyword, sortField, sortDir));

        // Trimitem parametrii înapoi la View
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "ticket/index";
    }

    // 2. FORMULAR CREARE
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        populateDropdowns(model); // Populăm dropdown-urile
        return "ticket/form";
    }

    // 3. SALVARE
    @PostMapping
    public String addTicket(@Valid @ModelAttribute Ticket ticket, BindingResult result, Model model) {
        // Validare standard
        if (result.hasErrors()) {
            populateDropdowns(model);
            return "ticket/form";
        }

        // Validare Business
        try {
            ticketService.saveTicket(ticket);
        } catch (IllegalArgumentException e) {
            handleBusinessException(e, result);
            populateDropdowns(model);
            return "ticket/form";
        }

        return "redirect:/tickets";
    }

    // 4. FORMULAR EDITARE
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("ticket", ticketService.getTicketById(id));
            populateDropdowns(model);
            return "ticket/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/tickets";
        }
    }

    // 5. UPDATE
    @PostMapping("/{id}/edit")
    public String updateTicket(@PathVariable Long id, @Valid @ModelAttribute Ticket ticket, BindingResult result, Model model) {
        if (result.hasErrors()) {
            ticket.setId(id);
            populateDropdowns(model);
            return "ticket/edit-form";
        }

        try {
            ticketService.updateTicket(id, ticket);
        } catch (IllegalArgumentException e) {
            handleBusinessException(e, result);
            ticket.setId(id);
            populateDropdowns(model);
            return "ticket/edit-form";
        }

        return "redirect:/tickets";
    }

    // 6. ȘTERGERE
    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "redirect:/tickets";
    }

    // 7. DETALII
    @GetMapping("/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("ticket", ticketService.getTicketById(id));
            return "ticket/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/tickets";
        }
    }

    // Metodă helper pentru a popula dropdown-urile
    // Folosește metodele getAll...() fără parametri din serviciile externe (asigură-te că există)
    private void populateDropdowns(Model model) {
        model.addAttribute("passengers", passengerService.getAllPassengers());
        model.addAttribute("flights", flightService.getAllFlights());
    }

    // Metodă helper pentru maparea erorilor
    private void handleBusinessException(IllegalArgumentException e, BindingResult result) {
        String msg = e.getMessage();
        if (msg.contains("pasager")) {
            result.rejectValue("passenger", "error.ticket", msg);
        } else if (msg.contains("zbor")) {
            result.rejectValue("flight", "error.ticket", msg);
        } else {
            result.reject("error.ticket", msg);
        }
    }
}