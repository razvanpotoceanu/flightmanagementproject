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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("passengers", passengerService.getAllPassengers());
        model.addAttribute("flights", flightService.getAllFlights());
        return "ticket/form";
    }

    @PostMapping
    public String addTicket(@Valid @ModelAttribute Ticket ticket, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("passengers", passengerService.getAllPassengers());
            model.addAttribute("flights", flightService.getAllFlights());
            return "ticket/form";
        }
        ticketService.saveTicket(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("ticket", ticketService.getTicketById(id));
            model.addAttribute("passengers", passengerService.getAllPassengers());
            model.addAttribute("flights", flightService.getAllFlights());
            return "ticket/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/tickets";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateTicket(@PathVariable Long id, @Valid @ModelAttribute Ticket ticket, BindingResult result, Model model) {
        if (result.hasErrors()) {
            ticket.setId(id);
            model.addAttribute("passengers", passengerService.getAllPassengers());
            model.addAttribute("flights", flightService.getAllFlights());
            return "ticket/edit-form";
        }
        ticketService.updateTicket(id, ticket);
        return "redirect:/tickets";
    }

    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "redirect:/tickets";
    }

    @GetMapping("/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("ticket", ticketService.getTicketById(id));
            return "ticket/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/tickets";
        }
    }
}