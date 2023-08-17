package com.example.HotelReservation.Repositories;

import com.example.HotelReservation.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findRoomsById(Long Id);
}
