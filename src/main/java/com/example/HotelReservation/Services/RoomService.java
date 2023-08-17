package com.example.HotelReservation.Services;

import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> getRoomsForHotel(Long id, Date startDate, Date endDate){
        List<Room> allRooms = roomRepository.findRoomsById(id);

        List<Room> availableRooms = allRooms.stream().filter(room -> room.isRoomAvailable(startDate, endDate)).toList();

        return availableRooms;
    }
}
