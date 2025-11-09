package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerRepository extends InFileRepository<Passenger, String> {

    // Constructorul apelează clasa părinte (InFileRepository)
    // și îi spune ce fișier JSON să folosească și ce tip de clasă să citească
    public PassengerRepository() {
        super("data/passengers.json", Passenger.class);
    }
}