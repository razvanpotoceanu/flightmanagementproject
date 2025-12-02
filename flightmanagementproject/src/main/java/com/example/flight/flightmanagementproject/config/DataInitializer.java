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
        // Verificăm dacă baza de date e goală pentru a nu duplica datele la fiecare restart
        if (passengerRepo.count() > 0) {
            return; // Dacă avem deja date, ne oprim.
        }

        System.out.println(">>> INIȚIALIZARE BAZĂ DE DATE START <<<");

        // 1. PASAGERI (Nu depind de nimeni)
        Passenger p1 = passengerRepo.save(new Passenger("Mihai Eminescu", "mihai@test.com", "RON"));
        Passenger p2 = passengerRepo.save(new Passenger("Ion Creanga", "ion@test.com", "RON"));
        Passenger p3 = passengerRepo.save(new Passenger("Veronica Micle", "veronica@test.com", "EUR"));
        Passenger p4 = passengerRepo.save(new Passenger("George Cosbuc", "george@test.com", "RON"));
        Passenger p5 = passengerRepo.save(new Passenger("Lucian Blaga", "lucian@test.com", "USD"));
        Passenger p6 = passengerRepo.save(new Passenger("Tudor Arghezi", "tudor@test.com", "RON"));
        Passenger p7 = passengerRepo.save(new Passenger("Ana Blandiana", "ana@test.com", "EUR"));
        Passenger p8 = passengerRepo.save(new Passenger("Nichita Stanescu", "nichita@test.com", "RON"));
        Passenger p9 = passengerRepo.save(new Passenger("Marin Sorescu", "marin@test.com", "RON"));
        Passenger p10 = passengerRepo.save(new Passenger("Mircea Eliade", "mircea@test.com", "USD"));


        // 2. AVIOANE (Nu depind de nimeni)
        Airplane a1 = airplaneRepo.save(new Airplane("Boeing 737", 180));
        Airplane a2 = airplaneRepo.save(new Airplane("Airbus A320", 150));
        Airplane a3 = airplaneRepo.save(new Airplane("Boeing 747", 400));
        Airplane a4 = airplaneRepo.save(new Airplane("Airbus A380", 500));
        Airplane a5 = airplaneRepo.save(new Airplane("Embraer E190", 100));
        Airplane a6 = airplaneRepo.save(new Airplane("Bombardier CRJ900", 90));
        Airplane a7 = airplaneRepo.save(new Airplane("ATR 72", 70));
        Airplane a8 = airplaneRepo.save(new Airplane("Boeing 777", 350));
        Airplane a9 = airplaneRepo.save(new Airplane("Airbus A350", 300));
        Airplane a10 = airplaneRepo.save(new Airplane("Boeing 787", 250));

        // 3. STAFF (Nu depind de nimeni)
        AirlineEmployee pilot1 = airlineEmployeeRepo.save(new AirlineEmployee("Traian Vuia", AirlineEmployeeRole.PILOT));
        AirlineEmployee copilot1 = airlineEmployeeRepo.save(new AirlineEmployee("Henri Coanda", AirlineEmployeeRole.CO_PILOT));
        AirlineEmployee purser1 = airlineEmployeeRepo.save(new AirlineEmployee("Smaranda Braescu", AirlineEmployeeRole.CHIEF_PURSER));
        AirlineEmployee att1 = airlineEmployeeRepo.save(new AirlineEmployee("Nadia Comaneci", AirlineEmployeeRole.FLIGHT_ATTENDANT));
        AirlineEmployee att2 = airlineEmployeeRepo.save(new AirlineEmployee("Gheorghe Hagi", AirlineEmployeeRole.FLIGHT_ATTENDANT));
        AirlineEmployee pilot2 = airlineEmployeeRepo.save(new AirlineEmployee("Simona Halep", AirlineEmployeeRole.PILOT));
        AirlineEmployee crew1 = airlineEmployeeRepo.save(new AirlineEmployee("Ion Tiriac", AirlineEmployeeRole.GROUND_CREW));
        AirlineEmployee copilot2 = airlineEmployeeRepo.save(new AirlineEmployee("Ilie Nastase", AirlineEmployeeRole.CO_PILOT));
        AirlineEmployee att3 = airlineEmployeeRepo.save(new AirlineEmployee("Maria Tanase", AirlineEmployeeRole.FLIGHT_ATTENDANT));
        AirlineEmployee purser2 = airlineEmployeeRepo.save(new AirlineEmployee("George Enescu", AirlineEmployeeRole.CHIEF_PURSER));

        AirportEmployee sec1 = airportEmployeeRepo.save(new AirportEmployee("Aurel Vlaicu", "Sef Securitate", "Securitate"));
        AirportEmployee med1 = airportEmployeeRepo.save(new AirportEmployee("Ana Aslan", "Medic", "Medical"));
        AirportEmployee eng1 = airportEmployeeRepo.save(new AirportEmployee("Elisa Leonida", "Inginer", "Mentenanta"));
        AirportEmployee bio1 = airportEmployeeRepo.save(new AirportEmployee("Emil Racovita", "Biolog", "Cercetare"));
        AirportEmployee doc2 = airportEmployeeRepo.save(new AirportEmployee("Victor Babes", "Medic", "Medical"));
        AirportEmployee insp1 = airportEmployeeRepo.save(new AirportEmployee("Spiru Haret", "Inspector", "Control"));
        AirportEmployee mgr1 = airportEmployeeRepo.save(new AirportEmployee("Grigore Antipa", "Manager", "Administrativ"));
        AirportEmployee arh1 = airportEmployeeRepo.save(new AirportEmployee("Anghel Saligny", "Arhitect", "Infrastructura"));
        AirportEmployee farm1 = airportEmployeeRepo.save(new AirportEmployee("Carol Davila", "Farmacist", "Medical"));
        AirportEmployee it1 = airportEmployeeRepo.save(new AirportEmployee("Petrache Poenaru", "Inginer IT", "Tehnic"));


        // 4. NOTICE BOARDS (Nu depind de nimeni)
        NoticeBoard nb1 = noticeBoardRepo.save(new NoticeBoard(LocalDate.now()));
        NoticeBoard nb2 = noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusDays(1)));
        NoticeBoard nb3 = noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusDays(2)));
        NoticeBoard nb4 = noticeBoardRepo.save(new NoticeBoard(LocalDate.now().minusDays(1)));
        NoticeBoard nb5 = noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusWeeks(1)));
        NoticeBoard nb6 = noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusWeeks(2)));
        NoticeBoard nb7 = noticeBoardRepo.save(new NoticeBoard(LocalDate.of(2025, 12, 25)));
        NoticeBoard nb8 = noticeBoardRepo.save(new NoticeBoard(LocalDate.of(2026, 1, 1)));
        NoticeBoard nb9 = noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusMonths(1)));
        NoticeBoard nb10 = noticeBoardRepo.save(new NoticeBoard(LocalDate.now().plusMonths(2)));


        // 5. ZBORURI (Depind de Airplane și NoticeBoard - DECI LE SALVĂM ACUM)
        // Este CRITIC să folosim obiectele 'a1', 'nb1' salvate mai sus, nu new Airplane()

        Flight f1 = new Flight("RO-201", "08:00", "09:00");
        f1.setAirplane(a1);
        f1.setNoticeBoard(nb1);
        f1 = flightRepo.save(f1); // Salvăm și reținem referința salvată

        Flight f2 = new Flight("WIZZ-100", "10:00", "12:00");
        f2.setAirplane(a2);
        f2.setNoticeBoard(nb1);
        f2 = flightRepo.save(f2);

        Flight f3 = new Flight("RYAN-50", "14:00", "16:00");
        f3.setAirplane(a1); // Același avion face altă cursă
        f3.setNoticeBoard(nb2);
        f3 = flightRepo.save(f3);

        Flight f4 = new Flight("LUFT-99", "18:00", "20:00");
        f4.setAirplane(a3);
        f4.setNoticeBoard(nb3);
        f4 = flightRepo.save(f4);

        Flight f5 = new Flight("TAROM-300", "07:00", "08:30");
        f5.setAirplane(a4);
        f5.setNoticeBoard(nb4);
        f5 = flightRepo.save(f5);

        Flight f6 = new Flight("BLUE-10", "22:00", "23:30");
        f6.setAirplane(a5);
        f6.setNoticeBoard(nb5);
        f6 = flightRepo.save(f6);

        Flight f7 = new Flight("AF-555", "09:00", "11:00");
        f7.setAirplane(a6);
        f7.setNoticeBoard(nb6);
        f7 = flightRepo.save(f7);

        Flight f8 = new Flight("KLM-777", "13:00", "15:00");
        f8.setAirplane(a7);
        f8.setNoticeBoard(nb7);
        f8 = flightRepo.save(f8);

        Flight f9 = new Flight("BA-222", "17:00", "19:00");
        f9.setAirplane(a8);
        f9.setNoticeBoard(nb8);
        f9 = flightRepo.save(f9);

        Flight f10 = new Flight("AUSTRIAN-1", "21:00", "22:00");
        f10.setAirplane(a9);
        f10.setNoticeBoard(nb9);
        f10 = flightRepo.save(f10);


        // 6. BILETE (Depind de Flight și Passenger - LE SALVĂM ACUM)
        // Folosind obiectele p1 și f1 ne asigurăm că ID-urile sunt corecte
        Ticket t1 = ticketRepo.save(new Ticket(100.0, "1A", p1, f1));
        Ticket t2 = ticketRepo.save(new Ticket(150.0, "1B", p2, f1));
        Ticket t3 = ticketRepo.save(new Ticket(200.0, "2A", p3, f2));
        Ticket t4 = ticketRepo.save(new Ticket(50.0, "10C", p4, f2));
        Ticket t5 = ticketRepo.save(new Ticket(300.0, "5F", p5, f3));
        Ticket t6 = ticketRepo.save(new Ticket(120.0, "12D", p6, f4));
        Ticket t7 = ticketRepo.save(new Ticket(90.0, "3A", p7, f5));
        Ticket t8 = ticketRepo.save(new Ticket(85.0, "3B", p8, f5));
        Ticket t9 = ticketRepo.save(new Ticket(500.0, "1A", p9, f6));
        Ticket t10 = ticketRepo.save(new Ticket(450.0, "1B", p10, f6));

        // 7. BAGAJE (Depind de Ticket - LE SALVĂM ACUM)
        luggageRepo.save(new Luggage(t1, LuggageStatus.CHECKED_IN));
        luggageRepo.save(new Luggage(t2, LuggageStatus.LOADED));
        luggageRepo.save(new Luggage(t3, LuggageStatus.DELIVERED));
        luggageRepo.save(new Luggage(t4, LuggageStatus.CHECKED_IN));
        luggageRepo.save(new Luggage(t5, LuggageStatus.LOST));
        luggageRepo.save(new Luggage(t6, LuggageStatus.CHECKED_IN));
        luggageRepo.save(new Luggage(t7, LuggageStatus.LOADED));
        luggageRepo.save(new Luggage(t8, LuggageStatus.DELIVERED));
        luggageRepo.save(new Luggage(t9, LuggageStatus.CHECKED_IN));
        luggageRepo.save(new Luggage(t10, LuggageStatus.CHECKED_IN));

        // 8. ASIGNĂRI (Depind de Flight și Staff - LE SALVĂM ACUM)
        // Zborul 1 are un pilot și un om de securitate
        assignmentRepo.save(new FlightAssignment(f1, pilot1));
        assignmentRepo.save(new FlightAssignment(f1, sec1));

        // Zborul 2 are copilot
        assignmentRepo.save(new FlightAssignment(f2, copilot1));

        // Alte asignări pentru cele 10 elemente
        assignmentRepo.save(new FlightAssignment(f3, purser1));
        assignmentRepo.save(new FlightAssignment(f4, att1));
        assignmentRepo.save(new FlightAssignment(f5, att2));
        assignmentRepo.save(new FlightAssignment(f6, pilot2));
        assignmentRepo.save(new FlightAssignment(f7, crew1));
        assignmentRepo.save(new FlightAssignment(f8, copilot2));
        assignmentRepo.save(new FlightAssignment(f9, att3));


        System.out.println(">>> INIȚIALIZARE BAZĂ DE DATE COMPLETĂ <<<");
    }
}