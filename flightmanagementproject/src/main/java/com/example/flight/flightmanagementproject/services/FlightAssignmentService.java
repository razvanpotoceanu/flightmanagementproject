package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.FlightAssignment;
import com.example.flight.flightmanagementproject.repositories.AirlineEmployeeRepository;
import com.example.flight.flightmanagementproject.repositories.AirportEmployeeRepository;
import com.example.flight.flightmanagementproject.repositories.FlightAssignmentRepository;
import com.example.flight.flightmanagementproject.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightAssignmentService {

    private final FlightAssignmentRepository repository;
    // Injectăm repo-urile externe pentru a valida existența ID-urilor
    private final FlightRepository flightRepository;
    private final AirlineEmployeeRepository airlineEmployeeRepository;
    private final AirportEmployeeRepository airportEmployeeRepository;

    @Autowired
    public FlightAssignmentService(FlightAssignmentRepository repository,
                                   FlightRepository flightRepository,
                                   AirlineEmployeeRepository airlineEmployeeRepository,
                                   AirportEmployeeRepository airportEmployeeRepository) {
        this.repository = repository;
        this.flightRepository = flightRepository;
        this.airlineEmployeeRepository = airlineEmployeeRepository;
        this.airportEmployeeRepository = airportEmployeeRepository;
    }

    public List<FlightAssignment> getAllFlightAssignments() {
        return repository.findAll();
    }

    public FlightAssignment getFlightAssignmentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignarea nu a fost găsită cu id: " + id));
    }

    public void saveFlightAssignment(FlightAssignment assignment) {
        // Validăm consistența datelor înainte de salvare
        validateAssignment(assignment);

        // Protecție ID
        if (assignment.getId() != null && repository.existsById(assignment.getId())) {
            throw new IllegalArgumentException("Există deja o asignare cu acest ID.");
        }

        repository.save(assignment);
    }

    public void updateFlightAssignment(Long id, FlightAssignment updatedAssignment) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nu se poate actualiza. Asignare negăsită cu ID: " + id);
        }

        FlightAssignment existing = getFlightAssignmentById(id);

        // Setăm noile valori pentru a le valida
        existing.setFlight(updatedAssignment.getFlight());
        existing.setAirlineEmployee(updatedAssignment.getAirlineEmployee());
        existing.setAirportEmployee(updatedAssignment.getAirportEmployee());

        validateAssignment(existing);

        repository.save(existing);
    }

    // Metodă privată pentru validarea regulilor de business
    private void validateAssignment(FlightAssignment assignment) {
        // 1. Validare Zbor (Trebuie să fie selectat și să existe în DB)
        if (assignment.getFlight() == null || assignment.getFlight().getId() == null) {
            throw new IllegalArgumentException("Trebuie selectat un zbor valid.");
        }
        if (!flightRepository.existsById(assignment.getFlight().getId())) {
            throw new IllegalArgumentException("Zborul selectat (ID: " + assignment.getFlight().getId() + ") nu există în baza de date.");
        }

        // 2. Validare Angajați (Trebuie să fie unul sau altul, nu ambii null)
        boolean hasAirlineEmp = assignment.getAirlineEmployee() != null && assignment.getAirlineEmployee().getId() != null;
        boolean hasAirportEmp = assignment.getAirportEmployee() != null && assignment.getAirportEmployee().getId() != null;

        if (!hasAirlineEmp && !hasAirportEmp) {
            throw new IllegalArgumentException("Trebuie selectat cel puțin un angajat (Airline sau Airport).");
        }

        // 3. Validare existență Angajați în DB
        if (hasAirlineEmp && !airlineEmployeeRepository.existsById(assignment.getAirlineEmployee().getId())) {
            throw new IllegalArgumentException("Angajatul Airline selectat nu există în baza de date.");
        }
        if (hasAirportEmp && !airportEmployeeRepository.existsById(assignment.getAirportEmployee().getId())) {
            throw new IllegalArgumentException("Angajatul Airport selectat nu există în baza de date.");
        }
    }

    public void deleteFlightAssignment(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Asignarea nu a fost găsită cu id: " + id);
        }
        repository.deleteById(id);
    }
}