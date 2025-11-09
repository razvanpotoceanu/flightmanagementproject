package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Ticket;
import com.example.flight.flightmanagementproject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService service;

    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllTickets(Model model) {
        model.addAttribute("tickets", service.getAllTickets());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String showNewTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "ticket/form";
    }

    @PostMapping
    public RedirectView addTicket(@ModelAttribute Ticket ticket) throws RepositoryException {
        service.addTicket(ticket);
        return new RedirectView("/tickets");
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteTicket(@PathVariable String id) throws RepositoryException {
        service.deleteTicket(id);
        return new RedirectView("/tickets");
    }

    @GetMapping("/{id}")
    public String getTicketDetails(@PathVariable String id, Model model) {
        try {
            model.addAttribute("ticket", service.getTicketById(id));
            return "ticket/details";
        } catch (ResourceNotFoundException e) {
            return "redirect:/tickets";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditTicketForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("ticket", service.getTicketById(id));
            return "ticket/edit-form";
        } catch (ResourceNotFoundException e) {
            return "redirect:/tickets";
        }
    }

    @PostMapping("/{id}/edit")
    public RedirectView updateTicket(@PathVariable String id, @ModelAttribute Ticket ticket) {
        try {
            service.updateTicket(id, ticket);
        } catch (RepositoryException e) {
            // Logare
        }
        return new RedirectView("/tickets");
    }
}