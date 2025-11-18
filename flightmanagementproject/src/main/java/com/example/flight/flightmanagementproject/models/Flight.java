package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight extends BaseEntity {

    @NotBlank(message = "Numărul zborului este obligatoriu")
    private String flightNumber;

    @NotBlank(message = "Ora de plecare este obligatorie")
    private String departureTime;

    @NotBlank(message = "Ora de sosire este obligatorie")
    private String arrivalTime;

    // Relație: Mai multe zboruri pot folosi un avion (dar la momente diferite)
    @ManyToOne
    @JoinColumn(name = "airplane_id") // Cheie străină
    private Airplane airplane;

    // Relație: Un zbor are mai multe bilete vândute
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    // Relație: Un zbor poate apărea pe un panou de afișaj (NoticeBoard)
    @ManyToOne
    @JoinColumn(name = "notice_board_id")
    private NoticeBoard noticeBoard;

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
    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
    public NoticeBoard getNoticeBoard() { return noticeBoard; }
    public void setNoticeBoard(NoticeBoard noticeBoard) { this.noticeBoard = noticeBoard; }
}