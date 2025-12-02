package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.repositories.AirplaneRepository;
import com.example.flight.flightmanagementproject.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        validateFlight(flight);

        // Verificare duplicat la creare (pe numărul zborului)
        if (repository.existsByFlightNumber(flight.getFlightNumber())) {
            throw new IllegalArgumentException("Există deja un zbor cu numărul: " + flight.getFlightNumber());
        }

        // Verificare ID existent
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

        // Verificare duplicat la update (doar dacă numărul s-a schimbat)
        if (!existing.getFlightNumber().equals(updatedFlight.getFlightNumber()) &&
                repository.existsByFlightNumber(updatedFlight.getFlightNumber())) {
            throw new IllegalArgumentException("Numărul de zbor este deja folosit de alt zbor.");
        }

        // Setăm noile valori și validăm referințele
        // (facem o copie temporară pentru validare)
        Flight tempForValidation = new Flight();
        tempForValidation.setAirplane(updatedFlight.getAirplane());
        tempForValidation.setDepartureTime(updatedFlight.getDepartureTime());
        validateFlight(tempForValidation); // Validăm referințele

        existing.setFlightNumber(updatedFlight.getFlightNumber());
        existing.setDepartureTime(updatedFlight.getDepartureTime());
        existing.setArrivalTime(updatedFlight.getArrivalTime());
        existing.setAirplane(updatedFlight.getAirplane());
        existing.setNoticeBoard(updatedFlight.getNoticeBoard());

        repository.save(existing);
    }

    private void validateFlight(Flight flight) {
        // Validare existență Avion
        if (flight.getAirplane() == null || flight.getAirplane().getId() == null) {
            throw new IllegalArgumentException("Trebuie selectat un avion.");
        }
        // Verificăm în DB dacă avionul există
        if (!airplaneRepository.existsById(flight.getAirplane().getId())) {
            throw new IllegalArgumentException("Avionul selectat nu există în baza de date.");
        }

        // Validare simplă de timp
        if (flight.getDepartureTime() == null || flight.getDepartureTime().trim().isEmpty()) {
            throw new IllegalArgumentException("Ora de plecare este obligatorie.");
        }
    }

    public void deleteFlight(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Zborul nu a fost găsit cu id: " + id);
        }
        repository.deleteById(id);
    }
}