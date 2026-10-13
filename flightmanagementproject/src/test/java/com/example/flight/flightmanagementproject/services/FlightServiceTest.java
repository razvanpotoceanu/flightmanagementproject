package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Airplane;
import com.example.flight.flightmanagementproject.models.Flight;
import com.example.flight.flightmanagementproject.repositories.AirplaneRepository;
import com.example.flight.flightmanagementproject.repositories.FlightRepository;
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
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AirplaneRepository airplaneRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight flight;
    private Airplane airplane;

    @BeforeEach
    void setUp() {
        airplane = new Airplane("Boeing 737", 150);
        airplane.setId(10L);

        flight = new Flight("RO123", "10:00", "12:00");
        flight.setId(1L);
        flight.setAirplane(airplane);
    }

    // --- TESTE PENTRU getAllFlights (Filtrare & Sortare) ---

    @Test
    void getAllFlights_WithKeyword_ReturnsFiltered() {
        when(flightRepository.findByFlightNumberContainingIgnoreCase(eq("RO"), any(Sort.class)))
                .thenReturn(Arrays.asList(flight));

        List<Flight> result = flightService.getAllFlights("RO", "flightNumber", "asc");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(flightRepository, times(1)).findByFlightNumberContainingIgnoreCase(eq("RO"), any(Sort.class));
    }

    // --- TESTE PENTRU saveFlight ---

    @Test
    void saveFlight_ValidFlight_SavesSuccessfully() {
        // Arrange
        Flight newFlight = new Flight("W6321", "14:00", "16:30");
        newFlight.setAirplane(airplane);

        when(airplaneRepository.existsById(10L)).thenReturn(true);
        when(flightRepository.existsByFlightNumber("W6321")).thenReturn(false);

        // Act
        flightService.saveFlight(newFlight);

        // Assert
        verify(flightRepository, times(1)).save(newFlight);
    }

    @Test
    void saveFlight_NullAirplane_ThrowsIllegalArgumentException() {
        // Arrange
        Flight invalidFlight = new Flight("W6321", "14:00", "16:30");
        invalidFlight.setAirplane(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> flightService.saveFlight(invalidFlight));
        verify(flightRepository, never()).save(any());
    }

    @Test
    void saveFlight_InvalidTimes_ThrowsIllegalArgumentException() {
        // Arrange
        Flight invalidFlight = new Flight("W6321", "16:00", "14:30"); // Arrival before departure
        invalidFlight.setAirplane(airplane);
        when(airplaneRepository.existsById(10L)).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> flightService.saveFlight(invalidFlight));
        assertTrue(exception.getMessage().contains("după ora de plecare"));
        verify(flightRepository, never()).save(any());
    }

    @Test
    void saveFlight_DuplicateFlightNumber_ThrowsIllegalArgumentException() {
        // Arrange
        Flight newFlight = new Flight("RO123", "14:00", "16:30");
        newFlight.setAirplane(airplane);

        when(airplaneRepository.existsById(10L)).thenReturn(true);
        when(flightRepository.existsByFlightNumber("RO123")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> flightService.saveFlight(newFlight));
        verify(flightRepository, never()).save(any());
    }

    // --- TESTE PENTRU updateFlight ---

    @Test
    void updateFlight_FlightNotExists_ThrowsResourceNotFoundException() {
        when(flightRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> flightService.updateFlight(99L, flight));
    }
}