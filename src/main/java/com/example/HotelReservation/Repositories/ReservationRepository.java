package com.example.HotelReservation.Repositories;

import com.example.HotelReservation.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
