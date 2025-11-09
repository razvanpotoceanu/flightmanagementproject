package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Passenger;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
// Importăm implementarea specifică, dar o folosim ca interfață
import com.example.flight.flightmanagementproject.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PassengerService {

    // Folosim interfața generică, nu implementarea specifică
    private final AbstractRepository<Passenger, String> passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassengerById(String id) throws ResourceNotFoundException {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger with id " + id + " not found."));
    }

    // Am schimbat metoda ta de "create"
    public Passenger addPassenger(Passenger passenger) {
        // Logica de business: ne asigurăm că are un ID nou
        if (passenger.getId() == null || passenger.getId().isEmpty()) {
            passenger.setId(UUID.randomUUID().toString());
        }
        // Inițializăm listele goale dacă sunt null, pentru a evita NullPointerException
        if (passenger.getTickets() == null) {
            passenger.setTickets(new ArrayList<>());
        }

        return passengerRepository.save(passenger);
    }

    public void deletePassenger(String id) {
        // Verificăm dacă există înainte de a șterge
        Passenger passenger = getPassengerById(id); // Aruncă excepție dacă nu există
        passengerRepository.deleteById(passenger.getId());
    }
    public Passenger updatePassenger(String id, Passenger passenger) throws RepositoryException {
        // Setăm ID-ul din URL
        passenger.setId(id);

        // Ne asigurăm că lista de bilete nu devine null la actualizare
        if (passenger.getTickets() == null) {
            passenger.setTickets(new ArrayList<>());
        }

        return passengerRepository.save(passenger);
    }




}