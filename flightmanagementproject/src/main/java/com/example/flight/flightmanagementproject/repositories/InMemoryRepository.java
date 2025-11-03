package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.models.BaseEntity;

import java.util.List;
import java.util.Objects; // IMPORT NECESAR
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class InMemoryRepository<T extends BaseEntity, ID> implements AbstractRepository<T, ID> {

    protected final List<T> store = new CopyOnWriteArrayList<>();

    @Override
    public T save(T entity) throws RepositoryException {
        if (entity.getId() == null) {
            // Service-ul ar fi trebuit să genereze un ID. Aruncăm o eroare clară.
            throw new RepositoryException("Eroare: S-a încercat salvarea unei entități cu ID null.");
        }

        // CORECȚIE AICI: Folosim Objects.equals() pentru a preveni NullPointerException
        // dacă un 'e.getId()' din listă este null (de la date de test vechi, etc.)
        store.removeIf(e -> Objects.equals(e.getId(), entity.getId()));

        store.add(entity);
        return entity;
    }

    @Override
    public List<T> findAll() {
        return store;
    }

    @Override
    public Optional<T> findById(ID id) {
        if (id == null) {
            return Optional.empty();
        }

        // CORECȚIE AICI: Folosim Objects.equals()
        return store.stream()
                .filter(entity -> Objects.equals(entity.getId(), id))
                .findFirst();
    }

    @Override
    public void deleteById(ID id) throws RepositoryException {
        if (id == null) {
            return;
        }

        // CORECȚIE AICI: Folosim Objects.equals()
        store.removeIf(entity -> Objects.equals(entity.getId(), id));
    }

    @Override
    public boolean existsById(ID id) {
        if (id == null) {
            return false;
        }

        // CORECȚIE AICI: Folosim Objects.equals()
        return store.stream().anyMatch(entity -> Objects.equals(entity.getId(), id));
    }
}