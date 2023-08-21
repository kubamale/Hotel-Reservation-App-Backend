package com.example.HotelReservation.Services;

import com.example.HotelReservation.DTOs.RoomDTO;
import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Repositories.HotelRepository;
import com.example.HotelReservation.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    public List<RoomDTO> getRoomsForHotel(Long id, Date startDate, Date endDate){
        List<Room> allRooms = roomRepository.findRoomsById(id);

        return allRooms.stream().filter(room -> room.isRoomAvailable(startDate, endDate))
                .map(Room::mapToDTO).toList();
    }

    public Room createNewRoom(RoomDTO roomDTO) {
        Optional<Hotel> hotel = hotelRepository.findById(roomDTO.hotelId);
        if (hotel.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "no hotel with that Id");
        }

        Room room = new Room(roomDTO.equipment, roomDTO.price, hotel.get(), roomDTO.capacity);
        roomRepository.save(room);
        return room;
    }

    public Long deleteRoom(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);

        if (room.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "No room with that Id");
        if(!room.get().reservations.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(405), "You cant delete this room there are reservations for it");
        roomRepository.delete(room.get());
        return room.get().id;
    }
}
