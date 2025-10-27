package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.models.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

// Implementare generică care folosește List (thread-safe)
public abstract class InMemoryRepository<T extends BaseEntity, ID> implements AbstractRepository<T, ID> {

    // Folosim List, ca în modelul "InMemoryDeviceRepository"
    // CopyOnWriteArrayList este thread-safe pentru citiri/scrieri
    protected final List<T> store = new CopyOnWriteArrayList<>();

    @Override
    public T save(T entity) throws RepositoryException {
        // Logica de "upsert" (update sau insert)
        // Înlăturăm versiunea veche dacă există (update)
        store.removeIf(e -> e.getId().equals(entity.getId()));
        // Adăugăm versiunea nouă (insert/update)
        store.add(entity);
        return entity;
    }

    @Override
    public List<T> findAll() {
        return store;
    }

    @Override
    public Optional<T> findById(ID id) {
        // Căutăm prin listă, exact ca în modelul profesoarei
        return store.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(ID id) throws RepositoryException {
        store.removeIf(entity -> entity.getId().equals(id));
    }

    @Override
    public boolean existsById(ID id) {
        return store.stream().anyMatch(entity -> entity.getId().equals(id));
    }
}