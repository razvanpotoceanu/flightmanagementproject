    package com.example.flight.flightmanagementproject.repositories;

    import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
    import com.example.flight.flightmanagementproject.models.BaseEntity;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.databind.SerializationFeature;
    import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Pentru LocalDate
    import org.springframework.core.io.ClassPathResource; // Pentru a găsi fișierul

    import java.io.File;
    import java.io.IOException;
    import java.util.List;
    import java.util.Objects;
    import java.util.Optional;
    import java.util.concurrent.CopyOnWriteArrayList;

    // Aceasta este noua clasă de bază (Înlocuiește InMemoryRepository)
    public abstract class InFileRepository<T extends BaseEntity, ID> implements AbstractRepository<T, ID> {

        // 'store' este acum un cache în memorie pentru fișierul JSON
        protected final List<T> store;
        private final ObjectMapper mapper;
        private final String filePath;
        private final Class<T> entityType; // Tipul clasei (ex: Passenger.class)

        public InFileRepository(String filePath, Class<T> entityType) {
            this.filePath = filePath;
            this.entityType = entityType;

            // Configurăm Jackson Mapper (biblioteca JSON)
            this.mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Suport pentru LocalDate/Time
            mapper.enable(SerializationFeature.INDENT_OUTPUT); // Scrie JSON frumos formatat

            // Încărcăm datele din fișier la pornire
            this.store = new CopyOnWriteArrayList<>(loadFromFile());
        }

        /**
         * Încarcă datele din fișierul JSON la pornire.
         */
        private List<T> loadFromFile() {
            try {
                // Folosim ClassPathResource pentru a găsi fișierul în 'resources'
                File file = new ClassPathResource(filePath).getFile();
                if (file.exists() && file.length() > 0) {
                    // Citim fișierul și îl convertim într-o listă de obiecte de tip T
                    return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, entityType));
                }
            } catch (IOException e) {
                // Dacă fișierul e corupt sau nu poate fi citit, aruncăm o eroare la pornire
                throw new RuntimeException("Eroare fatală: Nu s-au putut încărca datele din: " + filePath, e);
            }
            // Dacă fișierul nu există, pornim cu o listă goală
            return new CopyOnWriteArrayList<>();
        }

        /**
         * Salvează starea curentă a listei 'store' înapoi în fișierul JSON.
         * Această metodă este apelată după fiecare modificare (save/delete).
         */
        private synchronized void saveToFile() throws RepositoryException {
            try {
                File file = new ClassPathResource(filePath).getFile();
                // Suprascriem fișierul cu datele curente din memorie
                mapper.writeValue(file, store);
            } catch (IOException e) {
                // Aruncăm excepția custom dacă salvarea eșuează
                throw new RepositoryException("Nu s-au putut salva datele în: " + filePath, e);
            }
        }

        @Override
        public T save(T entity) throws RepositoryException {
            if (entity.getId() == null) {
                throw new RepositoryException("Entitatea trebuie să aibă un ID înainte de salvare.");
            }

            // Logica de "upsert" (update sau insert), sigură la null
            store.removeIf(e -> Objects.equals(e.getId(), entity.getId()));
            store.add(entity);

            // Actualizăm fișierul JSON
            saveToFile();
            return entity;
        }

        @Override
        public List<T> findAll() {
            // Returnăm lista din memorie (care e sincronizată cu fișierul)
            return store;
        }

        @Override
        public Optional<T> findById(ID id) {
            if (id == null) {
                return Optional.empty();
            }
            return store.stream()
                    .filter(entity -> Objects.equals(entity.getId(), id))
                    .findFirst();
        }

        @Override
        public void deleteById(ID id) throws RepositoryException {
            if (id == null) {
                return;
            }
            // Ștergem din memorie
            store.removeIf(entity -> Objects.equals(entity.getId(), id));

            // Actualizăm fișierul JSON
            saveToFile();
        }

        @Override
        public boolean existsById(ID id) {
            if (id == null) {
                return false;
            }
            return store.stream().anyMatch(entity -> Objects.equals(entity.getId(), id));
        }
    }