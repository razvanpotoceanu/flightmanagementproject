package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.repositories.AirplaneRepository;
import com.example.flight.flightmanagementproject.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    /**
     * Metoda actualizată pentru Proiectul 5: Suportă sortare și filtrare.
     */
    public List<Flight> getAllFlights(String keyword, String sortField, String sortDir) {
        // 1. Configurare Sortare
        if (sortField == null || sortField.isEmpty()) {
            sortField = "id"; // Sortare implicită după ID
        }

        Sort sort = Sort.by(sortField);
        sort = "desc".equals(sortDir) ? sort.descending() : sort.ascending();

        // 2. Configurare Filtrare (Căutare după număr zbor)
        if (keyword != null && !keyword.isEmpty()) {
            return repository.findByFlightNumberContainingIgnoreCase(keyword, sort);
        }

        // 3. Returnează tot, sortat
        return repository.findAll(sort);
    }

    // Metoda veche (fără argumente) - Păstrată pentru compatibilitate
    public List<Flight> getAllFlights() {
        return repository.findAll();
    }

    public Flight getFlightById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zborul nu a fost găsit cu id: " + id));
    }

    public void saveFlight(Flight flight) {
        // Validări (copiate din versiunea ta anterioară)
        validateFlightReferences(flight);
        validateFlightTimes(flight);

        if (flight.getId() == null && repository.existsByFlightNumber(flight.getFlightNumber())) {
            throw new IllegalArgumentException("Există deja un zbor cu numărul: " + flight.getFlightNumber());
        }
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

        if (!existing.getFlightNumber().equals(updatedFlight.getFlightNumber()) &&
                repository.existsByFlightNumber(updatedFlight.getFlightNumber())) {
            throw new IllegalArgumentException("Numărul de zbor este deja folosit de alt zbor.");
        }

        // Facem o copie temporară pentru validare
        Flight temp = new Flight();
        temp.setAirplane(updatedFlight.getAirplane());
        temp.setDepartureTime(updatedFlight.getDepartureTime());
        temp.setArrivalTime(updatedFlight.getArrivalTime());

        validateFlightReferences(temp);
        validateFlightTimes(updatedFlight);

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

    private void validateFlightTimes(Flight flight) {
        if (flight.getDepartureTime() == null || flight.getDepartureTime().trim().isEmpty()) {
            throw new IllegalArgumentException("Ora de plecare este obligatorie.");
        }
        if (flight.getArrivalTime() == null || flight.getArrivalTime().trim().isEmpty()) {
            throw new IllegalArgumentException("Ora de sosire este obligatorie.");
        }

        try {
            LocalTime departure = LocalTime.parse(flight.getDepartureTime());
            LocalTime arrival = LocalTime.parse(flight.getArrivalTime());

            if (arrival.isBefore(departure) && !arrival.equals(departure)) {
                throw new IllegalArgumentException("Ora de sosire trebuie să fie după ora de plecare.");
            }

            if (arrival.equals(departure)) {
                throw new IllegalArgumentException("Orele nu pot fi identice.");
            }

        } catch (DateTimeParseException e) {
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