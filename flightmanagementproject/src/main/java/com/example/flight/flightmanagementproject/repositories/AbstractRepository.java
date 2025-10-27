package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.models.BaseEntity;

import java.util.List;
import java.util.Optional;

// Am folosit ID generic (String) și BaseEntity
public interface AbstractRepository<T extends BaseEntity, ID> {

    T save(T t) throws RepositoryException;

    List<T> findAll();

    // Folosim Optional în loc de null, e o practică mai bună decât în modelul ei
    Optional<T> findById(ID id);

    void deleteById(ID id) throws RepositoryException;

    boolean existsById(ID id);
}