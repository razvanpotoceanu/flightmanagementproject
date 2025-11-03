package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Staff;
import org.springframework.stereotype.Repository;

@Repository
public class StaffRepository extends InMemoryRepository<Staff, String> {
    // Implementarea este moștenită
    public StaffRepository() {

    }
}