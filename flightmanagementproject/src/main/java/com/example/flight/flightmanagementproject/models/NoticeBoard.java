package com.example.flight.flightmanagementproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notice_boards")
public class NoticeBoard extends BaseEntity {

    @NotNull(message = "Data este obligatorie")
    private LocalDate date;

    // Un panou afișează mai multe zboruri
    @OneToMany(mappedBy = "noticeBoard")
    private List<Flight> flightsOfTheDay = new ArrayList<>();

    public NoticeBoard() {}

    public NoticeBoard(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public List<Flight> getFlightsOfTheDay() { return flightsOfTheDay; }
    public void setFlightsOfTheDay(List<Flight> flightsOfTheDay) { this.flightsOfTheDay = flightsOfTheDay; }
}