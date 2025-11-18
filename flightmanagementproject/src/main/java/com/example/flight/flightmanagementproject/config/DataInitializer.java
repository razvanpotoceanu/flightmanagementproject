package com.example.flight.flightmanagementproject.config;

import com.example.flight.flightmanagementproject.enums.AirlineEmployeeRole;
import com.example.flight.flightmanagementproject.enums.LuggageStatus;
import com.example.flight.flightmanagementproject.models.*;
import com.example.flight.flightmanagementproject.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PassengerRepository passengerRepo;
    private final AirplaneRepository airplaneRepo;
    private final FlightRepository flightRepo;
    private final AirlineEmployeeRepository airlineEmployeeRepo;
    private final AirportEmployeeRepository airportEmployeeRepo;
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

        // 3. Populează Staff (Airline & Airport)
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

        // 4. Populează Panouri (NoticeBoards)
        // Trebuie să le salvăm ÎNAINTE de zboruri dacă vrem să le asociem
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

        // 5. Populează Zboruri (Cu relații către Avioane și Panouri)
        if (flightRepo.count() == 0) {
            // Preluăm listele existente pentru a crea relații
            List<Airplane> airplanes = airplaneRepo.findAll();
            List<NoticeBoard> boards = noticeBoardRepo.findAll();

            Flight f1 = new Flight("RO-201", "08:00", "09:00");
            if (!airplanes.isEmpty()) f1.setAirplane(airplanes.get(0)); // Boeing 737
            if (!boards.isEmpty()) f1.setNoticeBoard(boards.get(0));
            flightRepo.save(f1);

            Flight f2 = new Flight("WIZZ-100", "10:00", "12:00");
            if (airplanes.size() > 1) f2.setAirplane(airplanes.get(1)); // Airbus A320
            if (boards.size() > 1) f2.setNoticeBoard(boards.get(1));
            flightRepo.save(f2);

            Flight f3 = new Flight("RYAN-50", "14:00", "16:00");
            if (airplanes.size() > 2) f3.setAirplane(airplanes.get(2));
            flightRepo.save(f3);

            Flight f4 = new Flight("LUFT-99", "18:00", "20:00");
            if (airplanes.size() > 3) f4.setAirplane(airplanes.get(3));
            flightRepo.save(f4);

            Flight f5 = new Flight("TAROM-300", "07:00", "08:30");
            flightRepo.save(f5);

            Flight f6 = new Flight("BLUE-10", "22:00", "23:30");
            flightRepo.save(f6);

            Flight f7 = new Flight("AF-555", "09:00", "11:00");
            flightRepo.save(f7);

            Flight f8 = new Flight("KLM-777", "13:00", "15:00");
            flightRepo.save(f8);

            Flight f9 = new Flight("BA-222", "17:00", "19:00");
            flightRepo.save(f9);

            Flight f10 = new Flight("AUSTRIAN-1", "21:00", "22:00");
            flightRepo.save(f10);
        }

        // 6. Populează Bilete (Ticket) - Relații cu Pasageri și Zboruri
        if (ticketRepo.count() == 0) {
            List<Passenger> passengers = passengerRepo.findAll();
            List<Flight> flights = flightRepo.findAll();

            if (!passengers.isEmpty() && !flights.isEmpty()) {
                ticketRepo.save(new Ticket(100.0, "1A", passengers.get(0), flights.get(0)));
                ticketRepo.save(new Ticket(150.0, "1B", passengers.get(1), flights.get(0)));
                ticketRepo.save(new Ticket(200.0, "2A", passengers.get(2), flights.get(1)));
                ticketRepo.save(new Ticket(50.0, "10C", passengers.get(3), flights.get(1)));
                ticketRepo.save(new Ticket(300.0, "5F", passengers.get(4), flights.get(2)));
                ticketRepo.save(new Ticket(120.0, "12D", passengers.get(5), flights.get(3)));
                ticketRepo.save(new Ticket(90.0, "3A", passengers.get(6), flights.get(4)));
                ticketRepo.save(new Ticket(85.0, "3B", passengers.get(7), flights.get(4)));
                ticketRepo.save(new Ticket(500.0, "1A", passengers.get(8), flights.get(5)));
                ticketRepo.save(new Ticket(450.0, "1B", passengers.get(9), flights.get(5)));
            }
        }

        // 7. Populează Bagaje (Luggage) - Relații cu Bilete
        if (luggageRepo.count() == 0) {
            List<Ticket> tickets = ticketRepo.findAll();

            if (!tickets.isEmpty()) {
                luggageRepo.save(new Luggage(tickets.get(0), LuggageStatus.CHECKED_IN));
                luggageRepo.save(new Luggage(tickets.get(0), LuggageStatus.LOADED));
                luggageRepo.save(new Luggage(tickets.get(1), LuggageStatus.CHECKED_IN));
                luggageRepo.save(new Luggage(tickets.get(2), LuggageStatus.DELIVERED));
                luggageRepo.save(new Luggage(tickets.get(3), LuggageStatus.CHECKED_IN));
                luggageRepo.save(new Luggage(tickets.get(4), LuggageStatus.LOST));
                luggageRepo.save(new Luggage(tickets.get(5), LuggageStatus.CHECKED_IN));
                luggageRepo.save(new Luggage(tickets.get(6), LuggageStatus.LOADED));
                luggageRepo.save(new Luggage(tickets.get(7), LuggageStatus.DELIVERED));
                luggageRepo.save(new Luggage(tickets.get(8), LuggageStatus.CHECKED_IN));
            }
        }

        // 8. Populează Asignări (FlightAssignments) - Relații Zbor-Angajat
        if (assignmentRepo.count() == 0) {
            List<Flight> flights = flightRepo.findAll();
            List<AirlineEmployee> airlineStaff = airlineEmployeeRepo.findAll();
            List<AirportEmployee> airportStaff = airportEmployeeRepo.findAll();

            if (!flights.isEmpty()) {
                // Asignăm piloți și echipaj la zboruri
                if (!airlineStaff.isEmpty()) {
                    assignmentRepo.save(new FlightAssignment(flights.get(0), airlineStaff.get(0))); // Pilot la Zbor 1
                    assignmentRepo.save(new FlightAssignment(flights.get(0), airlineStaff.get(1))); // Co-Pilot la Zbor 1
                    assignmentRepo.save(new FlightAssignment(flights.get(1), airlineStaff.get(2))); // Chief Purser la Zbor 2
                    assignmentRepo.save(new FlightAssignment(flights.get(2), airlineStaff.get(3))); // Flight Attendant la Zbor 3
                    assignmentRepo.save(new FlightAssignment(flights.get(3), airlineStaff.get(4))); // Flight Attendant la Zbor 4
                }

                // Asignăm personal aeroport la zboruri
                if (!airportStaff.isEmpty()) {
                    assignmentRepo.save(new FlightAssignment(flights.get(0), airportStaff.get(0))); // Securitate la Zbor 1
                    assignmentRepo.save(new FlightAssignment(flights.get(1), airportStaff.get(1))); // Mentenanță la Zbor 2
                    assignmentRepo.save(new FlightAssignment(flights.get(2), airportStaff.get(2))); // Medic la Zbor 3
                    assignmentRepo.save(new FlightAssignment(flights.get(4), airportStaff.get(5))); // Control la Zbor 5
                    assignmentRepo.save(new FlightAssignment(flights.get(5), airportStaff.get(6))); // Manager la Zbor 6
                }
            }
        }
    }
}