package com.example.flight.flightmanagementproject.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
// ... alte importuri ...

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class Staff extends BaseEntity {
    private String name;

    // ... (Constructorii și getName/setName rămân la fel) ...
    public Staff() {
        super();
    }
    public Staff(String id, String name) {
        super(id);
        this.name = name;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // METODĂ NOUĂ: Adaugă această metodă abstractă
    public abstract String getEmployeeType();
}