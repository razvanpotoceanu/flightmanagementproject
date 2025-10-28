package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Passenger;
import com.example.flight.flightmanagementproject.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // SCHIMBARE 1: Folosim @Controller
import org.springframework.ui.Model;             // Adaugă importul pentru Model
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView; // Adaugă importul pentru RedirectView

@Controller // SCHIMBARE 1: Trebuie să fie @Controller pentru a returna nume de template-uri
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService service;

    @Autowired
    public PassengerController(PassengerService service) {
        this.service = service;
    }

    // 1. GET ALL: Afișează lista de pasageri (index.html)
    // Ruta: GET /passengers
    @GetMapping
    // SCHIMBARE 2: Returnează String (nume template) și ia ca parametru Model
    public String getAllPassengers(Model model) {
        model.addAttribute("passengers", service.getAllPassengers());
        // Returnează calea către templates/passenger/index.html
        return "passenger/index";
    }

    // 2. GET NEW: Afișează formularul de creare (form.html)
    // Ruta: GET /passengers/new
    @GetMapping("/new")
    public String showNewPassengerForm(Model model) {
        // Punem un obiect 'Passenger' gol pentru a mapa câmpurile din formular
        model.addAttribute("passenger", new Passenger());
        // Returnează calea către templates/passenger/form.html
        return "passenger/form";
    }

    // 3. POST CREATE: Procesează formularul și salvează
    // Ruta: POST /passengers (Se folosește @ModelAttribute în loc de @RequestBody)
    @PostMapping
    public RedirectView addPassenger(@ModelAttribute Passenger passenger) {
        try {
            service.addPassenger(passenger);
        } catch (Exception e) {
            // Tratare simplă
        }
        // Redirecționează utilizatorul înapoi la lista actualizată
        return new RedirectView("/passengers");
    }

    // 4. POST DELETE: Șterge un pasager după ID (cerința cere POST pentru ștergere)
    // Ruta: POST /passengers/{id}/delete
    @PostMapping("/{id}/delete")
    public RedirectView deletePassenger(@PathVariable String id) {
        try {
            service.deletePassenger(id);
        } catch (ResourceNotFoundException e) {
            // Tratare simplă
        }
        // Redirecționează utilizatorul înapoi la lista actualizată
        return new RedirectView("/passengers");
    }

    // Metodele vechi GET /{id} și DELETE /{id} (din API-ul REST) sunt eliminate
    // deoarece nu sunt necesare pentru interfața web simplă cerută.
}