package com.example.HotelReservation.Repositories;

import com.example.HotelReservation.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.event.ListDataEvent;
import java.util.Date;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByUserId(long id);

}
