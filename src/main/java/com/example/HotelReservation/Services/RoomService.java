package com.example.HotelReservation.Services;

import com.example.HotelReservation.DTOs.InsertRoomDTO;
import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Repositories.HotelRepository;
import com.example.HotelReservation.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    public List<Room> getRoomsForHotel(Long id, Date startDate, Date endDate){
        List<Room> allRooms = roomRepository.findRoomsById(id);

        List<Room> availableRooms = allRooms.stream().filter(room -> room.isRoomAvailable(startDate, endDate)).toList();

        return availableRooms;
    }

    public Room createNewRoom(InsertRoomDTO roomDTO) {
        Optional<Hotel> hotel = hotelRepository.findById(roomDTO.hotelId);
        if (hotel.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "no hotel with that Id");
        }

        Room room = new Room(roomDTO.equipment, roomDTO.price, hotel.get(), roomDTO.capacity);
        roomRepository.save(room);
        return room;
    }
}
