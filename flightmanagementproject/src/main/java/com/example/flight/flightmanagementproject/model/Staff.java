package com.example.flight.flightmanagementproject.model;

// <abstract> din UML se traduce ca 'abstract class' în Java
public abstract class Staff {
    private String id;
    private String name;

    // Constructor
    public Staff(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}