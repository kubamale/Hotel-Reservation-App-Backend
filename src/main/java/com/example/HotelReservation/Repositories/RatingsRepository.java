package com.example.HotelReservation.Repositories;

import com.example.HotelReservation.Models.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
}
