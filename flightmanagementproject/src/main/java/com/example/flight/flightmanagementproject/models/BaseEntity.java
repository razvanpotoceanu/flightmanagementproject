package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import java.io.Serializable;

@MappedSuperclass // Spune JPA că aceasta este o clasă părinte pentru entități
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID Auto-incrementat de MySQL
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}