package com.example.flight.flightmanagementproject.models;

import com.example.flight.flightmanagementproject.enums.EmployeeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

// Nu mai este abstractă și nu mai are nevoie de @JsonTypeInfo
@JsonIgnoreProperties(ignoreUnknown = true)
public class Staff extends BaseEntity {

    private String name;
    private EmployeeType employeeType; // Noul Enum

    // Câmpuri specifice Airline (pot fi null)
    private String role;
    private List<FlightAssignment> assignments;

    // Câmpuri specifice Airport (pot fi null)
    private String designation;
    private String department;

    // Constructor gol
    public Staff() {
        super();
        this.assignments = new ArrayList<>();
    }

    // Constructor (poți adăuga/scoate parametri după nevoie)
    public Staff(String id, String name, EmployeeType employeeType) {
        super(id);
        this.name = name;
        this.employeeType = employeeType;
        this.assignments = new ArrayList<>();
    }

    // Getters și Setters pentru TOATE câmpurile

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public EmployeeType getEmployeeType() { return employeeType; }
    public void setEmployeeType(EmployeeType employeeType) { this.employeeType = employeeType; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<FlightAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<FlightAssignment> assignments) { this.assignments = assignments; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    // Metoda pentru afișarea tipului în tabel (ca înainte)
    public String getEmployeeTypeDisplay() {
        if (this.employeeType == EmployeeType.AIRLINE_EMPLOYEE) {
            return "Companie Aeriană" + (role != null ? " (" + role + ")" : "");
        }
        if (this.employeeType == EmployeeType.AIRPORT_EMPLOYEE) {
            return "Aeroport" + (designation != null ? " (" + designation + ")" : "");
        }
        return "N/A";
    }
}