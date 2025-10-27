package com.example.flight.flightmanagementproject.exceptions;

// Folosită când căutăm o resursă (Zbor, Pasager) după ID și nu o găsim
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}