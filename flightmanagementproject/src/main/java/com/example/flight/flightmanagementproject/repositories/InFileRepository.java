package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.models.BaseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class InFileRepository<T extends BaseEntity, ID> implements AbstractRepository<T, ID> {

    protected final List<T> store;
    private final ObjectMapper mapper;
    private File file; // Nu mai este final, pentru a putea fi ajustat
    private final Class<T> entityType;

    public InFileRepository(String filePath, Class<T> entityType) {
        this.entityType = entityType;
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // --- LOGICA DE DETECTARE A CĂII CORECTE ---
        String projectRoot = System.getProperty("user.dir");
        String relativePath = "/src/main/resources/" + filePath;

        // 1. Încercăm calea standard
        File checkFile = new File(projectRoot + relativePath);

        // 2. Dacă nu există, verificăm dacă proiectul este într-un sub-folder (structură dublă)
        if (!checkFile.exists()) {
            // Încercăm să adăugăm numele proiectului în cale
            File nestedFile = new File(projectRoot + "/flightmanagementproject" + relativePath);
            if (nestedFile.exists()) {
                checkFile = nestedFile;
            }
        }

        this.file = checkFile;

        // Mesaj de diagnosticare în consolă
        if (this.file.exists()) {
            System.out.println("SUCCES: Fișier găsit la: " + this.file.getAbsolutePath());
        } else {
            System.err.println("EROARE CRITICĂ: Fișierul NU există la: " + this.file.getAbsolutePath());
            // Opțional: Poți crea fișierul gol aici dacă vrei să eviți erorile, dar mai bine îl lași să vezi
        }

        this.store = new CopyOnWriteArrayList<>(loadFromFile());
    }

    private List<T> loadFromFile() {
        try {
            if (file.exists() && file.length() > 0) {
                return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, entityType));
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului: " + file.getAbsolutePath());
            e.printStackTrace();
        }
        return new CopyOnWriteArrayList<>();
    }

    private synchronized void saveToFile() throws RepositoryException {
        try {
            // Ne asigurăm că folderul părinte există
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            mapper.writeValue(file, store);
        } catch (IOException e) {
            throw new RepositoryException("Nu s-au putut salva datele în fișier.", e);
        }
    }

    @Override
    public T save(T entity) throws RepositoryException {
        if (entity.getId() == null) {
            throw new RepositoryException("Entitatea nu are ID.");
        }

        store.removeIf(e -> Objects.equals(e.getId(), entity.getId()));
        store.add(entity);
        saveToFile();
        return entity;
    }

    @Override
    public List<T> findAll() {
        return store;
    }

    @Override
    public Optional<T> findById(ID id) {
        if (id == null) return Optional.empty();
        return store.stream().filter(e -> Objects.equals(e.getId(), id)).findFirst();
    }

    @Override
    public void deleteById(ID id) throws RepositoryException {
        if (id == null) return;
        store.removeIf(e -> Objects.equals(e.getId(), id));
        saveToFile();
    }

    @Override
    public boolean existsById(ID id) {
        if (id == null) return false;
        return store.stream().anyMatch(e -> Objects.equals(e.getId(), id));
    }
}