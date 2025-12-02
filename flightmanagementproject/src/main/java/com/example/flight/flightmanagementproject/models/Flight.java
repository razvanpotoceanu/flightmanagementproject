package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight extends BaseEntity {

    @NotBlank(message = "Numărul zborului este obligatoriu")
    @Column(unique = true)
    private String flightNumber;

    @NotBlank(message = "Ora de plecare este obligatorie")
    private String departureTime;

    @NotBlank(message = "Ora de sosire este obligatorie")
    private String arrivalTime;

    @NotNull(message = "Trebuie să selectați un avion")
    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name = "notice_board_id")
    private NoticeBoard noticeBoard;

    // --- CORECȚIE 1: Cascade pe Bilete ---
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    // --- CORECȚIE 2: Cascade pe Asignări (Acesta LIPSEA la tine!) ---
    // Fără asta, nu poți șterge zborul dacă are personal asignat
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightAssignment> flightAssignments = new ArrayList<>();

    public Flight() {}

    public Flight(String flightNumber, String departureTime, String arrivalTime) {
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // Getters și Setters
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    public Airplane getAirplane() { return airplane; }
    public void setAirplane(Airplane airplane) { this.airplane = airplane; }
    public NoticeBoard getNoticeBoard() { return noticeBoard; }
    public void setNoticeBoard(NoticeBoard noticeBoard) { this.noticeBoard = noticeBoard; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public List<FlightAssignment> getFlightAssignments() { return flightAssignments; }
    public void setFlightAssignments(List<FlightAssignment> flightAssignments) { this.flightAssignments = flightAssignments; }
}