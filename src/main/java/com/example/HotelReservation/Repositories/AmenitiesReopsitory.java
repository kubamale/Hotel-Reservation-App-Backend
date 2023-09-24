package com.example.HotelReservation.Repositories;

import com.example.HotelReservation.Models.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmenitiesReopsitory extends JpaRepository<Amenities, Long> {

    List<Amenities> getByHotelsId(long id);
}
