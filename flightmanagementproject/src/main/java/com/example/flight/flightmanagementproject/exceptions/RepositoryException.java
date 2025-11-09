package com.example.flight.flightmanagementproject.exceptions;

public class RepositoryException extends RuntimeException {

    // Constructorul 1 (pe care îl ai deja)
    public RepositoryException(String message) {
        super(message);
    }

    // Constructorul 2 (ADĂUGAT PENTRU A REZOLVA EROAREA)
    // Acesta acceptă și mesajul, și cauza originală (ex: IOException)
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}