package com.example.flight.flightmanagementproject.service;

import com.example.flight.flightmanagementproject.model.Airplane;
import com.example.flight.flightmanagementproject.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;

    @Autowired
    public AirplaneService(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public Airplane createAirplane(int number, String modelName) {
        String newId = UUID.randomUUID().toString();
        Airplane airplane = new Airplane(newId, number, new ArrayList<>(), modelName);
        return airplaneRepository.save(airplane);
    }

    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    public Optional<Airplane> getAirplaneById(String id) {
        return airplaneRepository.findById(id);
    }
}