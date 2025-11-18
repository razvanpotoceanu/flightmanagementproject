package com.example.flight.flightmanagementproject.config;

import com.example.flight.flightmanagementproject.enums.AirlineEmployeeRole;
import com.example.flight.flightmanagementproject.enums.LuggageStatus;
import com.example.flight.flightmanagementproject.models.*;
import com.example.flight.flightmanagementproject.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PassengerRepository passengerRepo;
    private final AirplaneRepository airplaneRepo;
    private final FlightRepository flightRepo;
    private final AirlineEmployeeRepository airlineEmployeeRepo; // NOU
    private final AirportEmployeeRepository airportEmployeeRepo; // NOU
    private final TicketRepository ticketRepo;
    private final LuggageRepository luggageRepo;
    private final FlightAssignmentRepository assignmentRepo;
    private final NoticeBoardRepository noticeBoardRepo;

    public DataInitializer(PassengerRepository passengerRepo, AirplaneRepository airplaneRepo,
                           FlightRepository flightRepo, AirlineEmployeeRepository airlineEmployeeRepo,
                           AirportEmployeeRepository airportEmployeeRepo, TicketRepository ticketRepo,
                           LuggageRepository luggageRepo, FlightAssignmentRepository assignmentRepo,
                           NoticeBoardRepository noticeBoardRepo) {
        this.passengerRepo = passengerRepo;
        this.airplaneRepo = airplaneRepo;
        this.flightRepo = flightRepo;
        this.airlineEmployeeRepo = airlineEmployeeRepo;
        this.airportEmployeeRepo = airportEmployeeRepo;
        this.ticketRepo = ticketRepo;
        this.luggageRepo = luggageRepo;
        this.assignmentRepo = assignmentRepo;
        this.noticeBoardRepo = noticeBoardRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        // 1. Populează Pasageri
        if (passengerRepo.count() == 0) {
            passengerRepo.save(new Passenger("Mihai Eminescu", "mihai@test.com", "RON"));
            passengerRepo.save(new Passenger("Ion Creanga", "ion@test.com", "RON"));
            passengerRepo.save(new Passenger("Veronica Micle", "veronica@test.com", "EUR"));
            passengerRepo.save(new Passenger("George Cosbuc", "george@test.com", "RON"));
            passengerRepo.save(new Passenger("Lucian Blaga", "lucian@test.com", "USD"));
            passengerRepo.save(new Passenger("Tudor Arghezi", "tudor@test.com", "RON"));
            passengerRepo.save(new Passenger("Ana Blandiana", "ana@test.com", "EUR"));
            passengerRepo.save(new Passenger("Nichita Stanescu", "nichita@test.com", "RON"));
            passengerRepo.save(new Passenger("Marin Sorescu", "marin@test.com", "RON"));
            passengerRepo.save(new Passenger("Mircea Eliade", "mircea@test.com", "USD"));
        }

        // 2. Populează Avioane
        if (airplaneRepo.count() == 0) {
            airplaneRepo.save(new Airplane("Boeing 737", 180));
            airplaneRepo.save(new Airplane("Airbus A320", 150));
            airplaneRepo.save(new Airplane("Boeing 747", 400));
            airplaneRepo.save(new Airplane("Airbus A380", 500));
            airplaneRepo.save(new Airplane("Embraer E190", 100));
            airplaneRepo.save(new Airplane("Bombardier CRJ900", 90));
            airplaneRepo.save(new Airplane("ATR 72", 70));
            airplaneRepo.save(new Airplane("Boeing 777", 350));
            airplaneRepo.save(new Airplane("Airbus A350", 300));
            airplaneRepo.save(new Airplane("Boeing 787", 250));
        }

        // 3. Populează Airline Employees (Angajați Companie)
        if (airlineEmployeeRepo.count() == 0) {
            airlineEmployeeRepo.save(new AirlineEmployee("Traian Vuia", AirlineEmployeeRole.PILOT));
            airlineEmployeeRepo.save(new AirlineEmployee("Henri Coanda", AirlineEmployeeRole.CO_PILOT));
            airlineEmployeeRepo.save(new AirlineEmployee("Smaranda Braescu", AirlineEmployeeRole.CHIEF_PURSER));
            airlineEmployeeRepo.save(new AirlineEmployee("Nadia Comaneci", AirlineEmployeeRole.FLIGHT_ATTENDANT));
            airlineEmployeeRepo.save(new AirlineEmployee("Gheorghe Hagi", AirlineEmployeeRole.FLIGHT_ATTENDANT));
            airlineEmployeeRepo.save(new AirlineEmployee("Simona Halep", AirlineEmployeeRole.PILOT));
            airlineEmployeeRepo.save(new AirlineEmployee("Ion Tiriac", AirlineEmployeeRole.GROUND_CREW));
            airlineEmployeeRepo.save(new AirlineEmployee("Ilie Nastase", AirlineEmployeeRole.CO_PILOT));
            airlineEmployeeRepo.save(new AirlineEmployee("Maria Tanase", AirlineEmployeeRole.FLIGHT_ATTENDANT));
            airlineEmployeeRepo.save(new AirlineEmployee("George Enescu", AirlineEmployeeRole.CHIEF_PURSER));
        }

        // 4. Populează Airport Employees (Angajați Aeroport)
        if (airportEmployeeRepo.count() == 0) {
            airportEmployeeRepo.save(new AirportEmployee("Aurel Vlaicu", "Sef Securitate", "Securitate"));
            airportEmployeeRepo.save(new AirportEmployee("Elisa Leonida", "Inginer", "Mentenanta"));
            airportEmployeeRepo.save(new AirportEmployee("Victor Babes", "Medic", "Medical"));
            airportEmployeeRepo.save(new AirportEmployee("Emil Racovita", "Biolog", "Cercetare"));
            airportEmployeeRepo.save(new AirportEmployee("Ana Aslan", "Medic Sef", "Geriatrie"));
            airportEmployeeRepo.save(new AirportEmployee("Spiru Haret", "Inspector", "Control"));
            airportEmployeeRepo.save(new AirportEmployee("Grigore Antipa", "Manager", "Administrativ"));
            airportEmployeeRepo.save(new AirportEmployee("Anghel Saligny", "Arhitect", "Infrastructura"));
            airportEmployeeRepo.save(new AirportEmployee("Carol Davila", "Farmacist", "Medical"));
            airportEmployeeRepo.save(new AirportEmployee("Petrache Poenaru", "Inginer IT", "Tehnic"));
        }

        // 5. Populează Zboruri
        if (flightRepo.count() == 0) {
            flightRepo.save(new Flight("RO-201", "08:00", "09:00"));
            flightRepo.save(new Flight("WIZZ-100", "10:00", "12:00"));
            flightRepo.save(new Flight("RYAN-50", "14:00", "16:00"));
            flightRepo.save(new Flight("LUFT-99", "18:00", "20:00"));
            flightRepo.save(new Flight("TAROM-300", "07:00", "08:30"));
            flightRepo.save(new Flight("BLUE-10", "22:00", "23:30"));
            flightRepo.save(new Flight("AF-555", "09:00", "11:00"));
            flightRepo.save(new Flight("KLM-777", "13:00", "15:00"));
            flightRepo.save(new Flight("BA-222", "17:00", "19:00"));
            flightRepo.save(new Flight("AUSTRIAN-1", "21:00", "22:00"));
        }

        // 6. Populează Panouri (NoticeBoards)
        if (noticeBoardRepo.count() == 0) {
            noticeBoardRepo.save(new NoticeBoard(LocalDate.now()));
            noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusDays(1)));
            noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusDays(2)));
            noticeBoardRepo.save(new NoticeBoard(LocalDate.now().minusDays(1)));
            noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusWeeks(1)));
            noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusWeeks(2)));
            noticeBoardRepo.save(new NoticeBoard(LocalDate.of(2025, 12, 25)));
            noticeBoardRepo.save(new NoticeBoard(LocalDate.of(2026, 1, 1)));
            noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusMonths(1)));
            noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusMonths(2)));
        }
    }
}