package com.example.flight.flightmanagementproject.exceptions;

// Folosită pentru erori generale la salvare/citire (dacă am folosi fișiere/baze de date)
public class RepositoryException extends RuntimeException {
    public RepositoryException(String message) {
        super(message);
    }
}