package com.example.flight.flightmanagementproject.service;

import com.example.flight.flightmanagementproject.model.AirlineEmployee;
import com.example.flight.flightmanagementproject.model.AirportEmployee;
import com.example.flight.flightmanagementproject.model.Staff;
import com.example.flight.flightmanagementproject.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Staff createAirlineEmployee(String name, String role) {
        String newId = UUID.randomUUID().toString();
        Staff employee = new AirlineEmployee(newId, name, role, new ArrayList<>());
        return staffRepository.save(employee);
    }

    public Staff createAirportEmployee(String name, String designation, String department) {
        String newId = UUID.randomUUID().toString();
        Staff employee = new AirportEmployee(newId, name, designation, department);
        return staffRepository.save(employee);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
}