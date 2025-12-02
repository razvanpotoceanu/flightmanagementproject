package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.repositories.AirplaneRepository;
import com.example.flight.flightmanagementproject.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository repository;
    private final AirplaneRepository airplaneRepository;

    @Autowired
    public FlightService(FlightRepository repository, AirplaneRepository airplaneRepository) {
        this.repository = repository;
        this.airplaneRepository = airplaneRepository;
    }

    public List<Flight> getAllFlights() {
        return repository.findAll();
    }

    public Flight getFlightById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zborul nu a fost găsit cu id: " + id));
    }

    public void saveFlight(Flight flight) {
        // 1. Validări de existență (Avion)
        validateFlightReferences(flight);

        // 2. Validări de Timp (Format și Logică)
        validateFlightTimes(flight);

        // 3. Validare duplicat număr zbor
        if (flight.getId() == null && repository.existsByFlightNumber(flight.getFlightNumber())) {
            throw new IllegalArgumentException("Există deja un zbor cu numărul: " + flight.getFlightNumber());
        }

        // 4. Validare ID existent (prevenire suprascriere)
        if (flight.getId() != null && repository.existsById(flight.getId())) {
            throw new IllegalArgumentException("Există deja un zbor cu ID-ul: " + flight.getId());
        }

        repository.save(flight);
    }

    public void updateFlight(Long id, Flight updatedFlight) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Zborul nu există.");
        }

        Flight existing = getFlightById(id);

        // Validare duplicat număr (doar dacă s-a schimbat)
        if (!existing.getFlightNumber().equals(updatedFlight.getFlightNumber()) &&
                repository.existsByFlightNumber(updatedFlight.getFlightNumber())) {
            throw new IllegalArgumentException("Numărul de zbor este deja folosit de alt zbor.");
        }

        // Validări referințe și timp
        // (Facem o copie temporară a obiectului pentru a valida relațiile înainte de a le seta)
        Flight temp = new Flight();
        temp.setAirplane(updatedFlight.getAirplane());
        temp.setDepartureTime(updatedFlight.getDepartureTime());
        temp.setArrivalTime(updatedFlight.getArrivalTime());

        validateFlightReferences(temp);
        validateFlightTimes(updatedFlight);

        // Setăm valorile
        existing.setFlightNumber(updatedFlight.getFlightNumber());
        existing.setDepartureTime(updatedFlight.getDepartureTime());
        existing.setArrivalTime(updatedFlight.getArrivalTime());
        existing.setAirplane(updatedFlight.getAirplane());
        existing.setNoticeBoard(updatedFlight.getNoticeBoard());

        repository.save(existing);
    }

    private void validateFlightReferences(Flight flight) {
        if (flight.getAirplane() == null || flight.getAirplane().getId() == null) {
            throw new IllegalArgumentException("Trebuie selectat un avion.");
        }
        if (!airplaneRepository.existsById(flight.getAirplane().getId())) {
            throw new IllegalArgumentException("Avionul selectat nu există în baza de date.");
        }
    }

    /**
     * Metoda de validare a orelor.
     * Verifică formatul și logica (Sosire > Plecare).
     */
    private void validateFlightTimes(Flight flight) {
        if (flight.getDepartureTime() == null || flight.getDepartureTime().trim().isEmpty()) {
            throw new IllegalArgumentException("Ora de plecare este obligatorie.");
        }
        if (flight.getArrivalTime() == null || flight.getArrivalTime().trim().isEmpty()) {
            throw new IllegalArgumentException("Ora de sosire este obligatorie.");
        }

        try {
            // Încercăm să convertim String-ul în LocalTime (Format așteptat: HH:mm)
            // Dacă utilizatorul a introdus "25:00" sau "text", aici va crăpa.
            LocalTime departure = LocalTime.parse(flight.getDepartureTime());
            LocalTime arrival = LocalTime.parse(flight.getArrivalTime());

            // Verificăm logica: Sosirea trebuie să fie după plecare
            if (arrival.isBefore(departure) && !arrival.equals(departure)) {
                // Notă: Dacă zborul trece de miezul nopții, această logică ar trebui să fie mai complexă (cu Date),
                // dar pentru un proiect simplu presupunem zboruri în aceeași zi.
                throw new IllegalArgumentException("Ora de sosire trebuie să fie după ora de plecare.");
            }

            if (arrival.equals(departure)) {
                throw new IllegalArgumentException("Ora de sosire nu poate fi identică cu cea de plecare.");
            }

        } catch (DateTimeParseException e) {
            // Dacă formatul nu e bun (ex: "8 AM" în loc de "08:00" sau "text")
            throw new IllegalArgumentException("Format oră invalid. Folosiți formatul HH:mm (ex: 14:30).");
        }
    }

    public void deleteFlight(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Zborul nu a fost găsit cu id: " + id);
        }
        repository.deleteById(id);
    }
}