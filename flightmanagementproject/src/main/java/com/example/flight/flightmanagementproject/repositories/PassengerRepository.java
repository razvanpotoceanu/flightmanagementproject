package com.example.flight.flightmanagementproject.repositories;

import com.example.flight.flightmanagementproject.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    // JpaRepository oferă deja metodele save(), findAll(), findById(), deleteById()
    // Nu mai trebuie să scrii nimic aici!
}