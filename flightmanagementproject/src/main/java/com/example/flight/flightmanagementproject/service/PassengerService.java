package com.example.flight.flightmanagementproject.service;

import com.example.flight.flightmanagementproject.model.Passenger;
import com.example.flight.flightmanagementproject.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> getPassengerById(String id) {
        return passengerRepository.findById(id);
    }

    // Exemplu de logică de business: crearea unui pasager
    public Passenger createPassenger(String name, String currency, String email) {
        String newId = UUID.randomUUID().toString();
        Passenger passenger = new Passenger(newId, name, currency, new ArrayList<>(), email);
        return passengerRepository.save(passenger);
    }

    public void deletePassenger(String id) {
        passengerRepository.delete(id);
    }
}