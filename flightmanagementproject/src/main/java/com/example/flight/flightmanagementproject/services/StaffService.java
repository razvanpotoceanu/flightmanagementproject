package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.RepositoryException;
import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Staff;
import com.example.flight.flightmanagementproject.repositories.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StaffService {

    private final AbstractRepository<Staff, String> staffRepository;

    @Autowired
    public StaffService(@Qualifier("staffRepository") AbstractRepository<Staff, String> staffRepository) {
        this.staffRepository = staffRepository;
    }

    // Metoda 'addStaff' este acum mai simplă
    public Staff addStaff(Staff staff) throws RepositoryException {
        if (staff.getId() == null || staff.getId().isEmpty()) {
            staff.setId(UUID.randomUUID().toString());
        }

        // Inițializăm lista de asignări dacă este null (doar pt Airline)
        if (staff.getAssignments() == null) {
            staff.setAssignments(new ArrayList<>());
        }

        return staffRepository.save(staff);
    }

    public Staff updateStaff(String id, Staff staff) throws RepositoryException {
        staff.setId(id);
        if (staff.getAssignments() == null) {
            staff.setAssignments(new ArrayList<>());
        }
        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(String id) throws ResourceNotFoundException {
        return staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff with id " + id + " not found."));
    }

    public void deleteStaff(String id) throws RepositoryException {
        if (!staffRepository.existsById(id)) {
            throw new ResourceNotFoundException("Staff with id " + id + " not found.");
        }
        staffRepository.deleteById(id);
    }
}