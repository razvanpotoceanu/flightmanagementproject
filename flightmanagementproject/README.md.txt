# Project 2 - Flight Management Application (Full MVC & List-Based Repository)

## Project Goal and Architecture

The goal was to fully implement the multi-layered architecture using **MVC (Model-View-Controller)** pattern, ensuring clean separation of concerns between layers.

**Key Architectural Change:** The persistence layer was migrated from using `Map` to **`java.util.List`** for all in-memory storage solutions, adhering strictly to the required implementation style.

## Design Decisions and Implementation

### A. Repository Implementation (List-Based Storage)

**Decision:** Implemented `AbstractRepository<T, String>` and a generic base class **`InMemoryRepository<T, ID>`** which exclusively uses `List<T>` for data storage.

**Justification:** This fulfills the requirement to use Lists. We handle lookups/updates by iterating through the list streams (e.g., using `.stream().filter()`), ensuring data integrity even without the direct key access of a Map.

### B. Service Layer Refinement

All major entities (`Flight`, `Airplane`, `Staff`, `Ticket`, `Luggage`, etc.) now have dedicated **Service** classes.
**Key Feature:** Services correctly throw **`ResourceNotFoundException`** if an entity ID is not found during a lookup operation.

### C. Model Extensions (BaseEntity & Polymorphism)

* **Uniformity:** All domain models (Flights, Passengers, etc.) now extend **`BaseEntity`** to ensure every entity has a unique String `id`.
* **Staff Polymorphism:** The abstract `Staff` model correctly uses **Jackson annotations (`@JsonTypeInfo`)** to allow Controllers to handle both `AirlineEmployee` and `AirportEmployee` objects through a single endpoint.

## How to Run the Application

The project is built using Maven (or Gradle, depending on your setup).

1.  **Prerequisite Check:** Ensure **OpenJDK 17** is installed and correctly configured as the **Project SDK** within IntelliJ IDEA.
2.  **Execution:** Open the terminal in the project root directory (`.../flightmanagementproject`).
3.  Execute the command:
    * **If using Maven:** `mvn clean install spring-boot:run`
    * **If using Gradle:** `.\gradlew.bat bootRun` (on Windows) or `./gradlew bootRun` (on Linux/Mac).

## Testing Endpoints

The application is a RESTful API running on **port 8080**. Test connectivity using Postman or your browser:

* **Test 1 (Status Check):** `http://localhost:8080/hello` (Should return: `Die Anwendung funktioniert!`)
* **Test 2 (Data Check):** Access the main entity endpoints (e.g., `http://localhost:8080/flights`, `http://localhost:8080/airplanes`).