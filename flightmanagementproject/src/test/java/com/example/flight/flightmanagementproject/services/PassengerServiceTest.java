package com.example.flight.flightmanagementproject.services;

import com.example.flight.flightmanagementproject.exceptions.ResourceNotFoundException;
import com.example.flight.flightmanagementproject.models.Passenger;
import com.example.flight.flightmanagementproject.repositories.PassengerRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = new Passenger("Ion Popescu", "ion@test.com", "RON");
        passenger.setId(1L);
    }

    // --- TESTE PENTRU getAllPassengers (Filtrare & Sortare) ---

    @Test
    void getAllPassengers_WithoutKeyword_ReturnsAllSorted() {
        // Arrange
        when(passengerRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(passenger));

        // Act
        List<Passenger> result = passengerService.getAllPassengers(null, "name", "asc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(passengerRepository, times(1)).findAll(any(Sort.class));
        verify(passengerRepository, never()).findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(anyString(), anyString(), any(Sort.class));
    }

    @Test
    void getAllPassengers_WithKeyword_ReturnsFilteredAndSorted() {
        // Arrange
        when(passengerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(eq("Ion"), eq("Ion"), any(Sort.class)))
                .thenReturn(Arrays.asList(passenger));

        // Act
        List<Passenger> result = passengerService.getAllPassengers("Ion", "email", "desc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(passengerRepository, times(1)).findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(eq("Ion"), eq("Ion"), any(Sort.class));
        verify(passengerRepository, never()).findAll(any(Sort.class));
    }

    // --- TESTE PENTRU getPassengerById ---

    @Test
    void getPassengerById_WhenExists_ReturnsPassenger() {
        // Arrange
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        // Act
        Passenger result = passengerService.getPassengerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Ion Popescu", result.getName());
    }

    @Test
    void getPassengerById_WhenNotExists_ThrowsResourceNotFoundException() {
        // Arrange
        when(passengerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> passengerService.getPassengerById(99L));
    }

    // --- TESTE PENTRU savePassenger ---

    @Test
    void savePassenger_NewPassengerValid_SavesSuccessfully() {
        // Arrange
        Passenger newPassenger = new Passenger("Ana", "ana@test.com", "EUR");
        // newPassenger.getId() is null
        when(passengerRepository.existsByEmail("ana@test.com")).thenReturn(false);

        // Act
        passengerService.savePassenger(newPassenger);

        // Assert
        verify(passengerRepository, times(1)).save(newPassenger);
    }

    @Test
    void savePassenger_NewPassengerEmailExists_ThrowsIllegalArgumentException() {
        // Arrange
        Passenger newPassenger = new Passenger("Ana", "ana@test.com", "EUR");
        when(passengerRepository.existsByEmail("ana@test.com")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> passengerService.savePassenger(newPassenger));
        assertTrue(exception.getMessage().contains("Există deja un pasager cu acest email"));
        verify(passengerRepository, never()).save(any(Passenger.class));
    }

    // --- TESTE PENTRU updatePassenger ---

    @Test
    void updatePassenger_ValidData_UpdatesSuccessfully() {
        // Arrange
        Passenger updatedInfo = new Passenger("Ion Updated", "ion_new@test.com", "USD");
        when(passengerRepository.existsById(1L)).thenReturn(true);
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        when(passengerRepository.existsByEmail("ion_new@test.com")).thenReturn(false);

        // Act
        passengerService.updatePassenger(1L, updatedInfo);

        // Assert
        verify(passengerRepository, times(1)).save(passenger);
        assertEquals("Ion Updated", passenger.getName());
        assertEquals("ion_new@test.com", passenger.getEmail());
    }

    @Test
    void updatePassenger_IdNotExists_ThrowsResourceNotFoundException() {
        // Arrange
        when(passengerRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> passengerService.updatePassenger(99L, new Passenger()));
        verify(passengerRepository, never()).save(any());
    }

    // --- TESTE PENTRU deletePassenger ---

    @Test
    void deletePassenger_WhenExists_DeletesSuccessfully() {
        // Arrange
        when(passengerRepository.existsById(1L)).thenReturn(true);

        // Act
        passengerService.deletePassenger(1L);

        // Assert
        verify(passengerRepository, times(1)).deleteById(1L);
    }
}