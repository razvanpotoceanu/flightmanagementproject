package com.example.flight.flightmanagementproject.models;

public class AirportEmployee extends BaseEntity {
    private String name;
    private String designation; // Ex: Șef Securitate
    private String department;  // Ex: Securitate

    public AirportEmployee() {}

    public AirportEmployee(String id, String name, String designation, String department) {
        super(id);
        this.name = name;
        this.designation = designation;
        this.department = department;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}