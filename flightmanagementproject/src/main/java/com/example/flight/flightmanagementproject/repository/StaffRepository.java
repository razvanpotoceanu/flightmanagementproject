package com.example.flight.flightmanagementproject.repository;

import com.example.flight.flightmanagementproject.model.Staff;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StaffRepository {

    private final Map<String, Staff> staffStore = new HashMap<>();

    public Staff save(Staff staff) {
        staffStore.put(staff.getId(), staff);
        return staff;
    }

    public List<Staff> findAll() {
        return new ArrayList<>(staffStore.values());
    }

    public Optional<Staff> findById(String id) {
        return Optional.ofNullable(staffStore.get(id));
    }

    public void delete(String id) {
        staffStore.remove(id);
    }
}