package com.example.flight.flightmanagementproject.models;

// O clasă de bază pentru a ne asigura că toate entitățile au un ID
public abstract class BaseEntity {
    private String id;

    public BaseEntity() {} // Constructor gol necesar

    public BaseEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}