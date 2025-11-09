package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Staff;
import org.springframework.stereotype.Repository;

@Repository
public class StaffRepository extends InFileRepository<Staff, String> {

    public StaffRepository() {
        // ATENȚIE: Folosim Staff.class, deoarece Jackson știe
        // să citească subtipurile datorită @JsonTypeInfo din model
        super("data/staff.json", Staff.class);
    }
}