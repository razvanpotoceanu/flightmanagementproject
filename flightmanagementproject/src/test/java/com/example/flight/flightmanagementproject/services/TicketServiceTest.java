package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.models.Passenger;
import com.example.flight.flightmanagementproject.models.Ticket;
import com.example.flight.flightmanagementproject.repositories.FlightRepository;
import com.example.flight.flightmanagementproject.repositories.PassengerRepository;
import com.example.flight.flightmanagementproject.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private Passenger passenger;
    private Flight flight;

    @BeforeEach
    void setUp() {
        passenger = new Passenger();
        passenger.setId(100L);

        flight = new Flight();
        flight.setId(200L);

        ticket = new Ticket(150.0, "12A", passenger, flight);
        ticket.setId(1L);
    }

    @Test
    void getAllTickets_WithKeyword_CallsSearch() {
        when(ticketRepository.search(eq("12A"), any(Sort.class))).thenReturn(Arrays.asList(ticket));

        List<Ticket> result = ticketService.getAllTickets("12A", "price", "desc");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).search(eq("12A"), any(Sort.class));
    }

    @Test
    void saveTicket_ValidTicket_SavesSuccessfully() {
        // Arrange
        Ticket newTicket = new Ticket(100.0, "1B", passenger, flight);
        when(passengerRepository.existsById(100L)).thenReturn(true);
        when(flightRepository.existsById(200L)).thenReturn(true);

        // Act
        ticketService.saveTicket(newTicket);

        // Assert
        verify(ticketRepository, times(1)).save(newTicket);
    }

    @Test
    void saveTicket_NegativePrice_ThrowsIllegalArgumentException() {
        // Arrange
        Ticket invalidTicket = new Ticket(-50.0, "1B", passenger, flight);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ticketService.saveTicket(invalidTicket));
        assertTrue(exception.getMessage().contains("pozitiv"));
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void saveTicket_PassengerNotExists_ThrowsIllegalArgumentException() {
        // Arrange
        Ticket newTicket = new Ticket(100.0, "1B", passenger, flight);
        when(passengerRepository.existsById(100L)).thenReturn(false); // Pasagerul nu e in DB

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ticketService.saveTicket(newTicket));
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void deleteTicket_WhenExists_DeletesSuccessfully() {
        when(ticketRepository.existsById(1L)).thenReturn(true);

        ticketService.deleteTicket(1L);

        verify(ticketRepository, times(1)).deleteById(1L);
    }
}