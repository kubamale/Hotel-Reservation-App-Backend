package com.example.HotelReservation.Repositories;

import com.example.HotelReservation.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Transactional
    List<Hotel> findByUserId(long id);

}
