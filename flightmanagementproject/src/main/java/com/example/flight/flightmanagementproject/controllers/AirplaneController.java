package com.example.flight.flightmanagementproject.controllers;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Airplane;
import com.example.flight.flightmanagementproject.services.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Clasa esențială pentru Thymeleaf
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/airplanes") // Ruta de bază: /airplanes
public class AirplaneController {

    private final AirplaneService service;

    @Autowired
    public AirplaneController(AirplaneService service) {
        this.service = service;
    }

    // 1. GET ALL: Afișează lista (index.html)
    // Ruta: GET /airplanes
    @GetMapping
    public String getAllAirplanes(Model model) {
        // Punem lista de avioane în obiectul 'Model'
        model.addAttribute("airplanes", service.getAllAirplanes());

        // Returnează calea către templates/airplane/index.html
        return "airplane/index";
    }

    // 2. GET NEW: Afișează formularul de creare (form.html)
    // Ruta: GET /airplanes/new
    @GetMapping("/new")
    public String showNewAirplaneForm(Model model) {
        // Punem un obiect 'Airplane' gol pentru a mapa câmpurile din formular
        model.addAttribute("airplane", new Airplane());

        // Returnează calea către templates/airplane/form.html
        return "airplane/form";
    }

    // 3. POST CREATE: Procesează formularul și salvează
    // Ruta: POST /airplanes (Metoda POST a formularului)
    @PostMapping
    // @ModelAttribute preia datele din formular și le mapează pe obiectul Airplane
    public RedirectView addAirplane(@ModelAttribute Airplane airplane) {
        try {
            service.addAirplane(airplane);
        } catch (RepositoryException e) {
            // Logare sau tratare simplă a erorii (fără validări complexe, conform cerinței)
        }
        // Redirecționează utilizatorul la lista actualizată
        return new RedirectView("/airplanes");
    }

    // 4. POST DELETE: Șterge un avion după ID (cerința folosește POST)
    // Ruta: POST /airplanes/{id}/delete
    @PostMapping("/{id}/delete")
    public RedirectView deleteAirplane(@PathVariable String id) {
        try {
            service.deleteAirplane(id);
        } catch (ResourceNotFoundException | RepositoryException e) {
            // Logare sau tratare
        }
        // Redirecționează utilizatorul la lista actualizată
        return new RedirectView("/airplanes");
    }

    // Metodele vechi de GET/DELETE/{id} și POST (cu @RequestBody) au fost eliminate,
    // deoarece nu sunt cerute pentru interfața web simplă.
}