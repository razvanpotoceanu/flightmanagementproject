package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Luggage;
import org.springframework.stereotype.Repository;

@Repository
public class LuggageRepository extends InFileRepository<Luggage, String> {

    /**
     * Constructorul apelează clasa părinte (InFileRepository)
     * și îi spune ce fișier JSON să folosească și ce tip de clasă să citească.
     */
    public LuggageRepository() {
        super("data/luggage.json", Luggage.class);
    }
}