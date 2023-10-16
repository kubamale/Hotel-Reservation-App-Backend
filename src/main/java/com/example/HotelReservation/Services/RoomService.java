package com.example.HotelReservation.Services;

import com.example.HotelReservation.DTOs.RoomDTO;
import com.example.HotelReservation.Exceptions.AppException;
import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Models.User;
import com.example.HotelReservation.Repositories.HotelRepository;
import com.example.HotelReservation.Repositories.RoomRepository;
import com.example.HotelReservation.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {


    private final RoomRepository roomRepository;

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public ResponseEntity<List<RoomDTO>> getRoomsForHotel(Long id, Date startDate, Date endDate){
        List<Room> allRooms = roomRepository.findRoomsById(id);

        return ResponseEntity.ok(allRooms.stream().filter(room -> room.isRoomAvailable(startDate, endDate))
                .map(Room::mapToDTO).toList());
    }

    public ResponseEntity<Room> createNewRoom(RoomDTO roomDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> oUser = userRepository.findByLogin(authentication.getName());
        Optional<Hotel> hotel = hotelRepository.findById(roomDTO.hotelId);
        if (hotel.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "no hotel with that Id");
        }

        if ((oUser.isEmpty() || !Objects.equals(oUser.get().getId(), hotel.get().getUser().getId())) && !oUser.get().getRole().getName().equals("ADMIN")){
            throw new AppException("You Can't perform this action", HttpStatus.BAD_REQUEST);
        }

        Room room = new Room(roomDTO.equipment, roomDTO.price, hotel.get(), roomDTO.capacity);
        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }

    public ResponseEntity<Long> deleteRoom(Long roomId) {

        Optional<Room> room = roomRepository.findById(roomId);

        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "No room with that Id");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> oUser = userRepository.findByLogin(authentication.getName());
        Optional<Hotel> hotel = hotelRepository.findById(room.get().getHotel().Id);

        if (oUser.isEmpty()){
            return ResponseEntity.badRequest().body((long) -1);
        }


        if (!Objects.equals(oUser.get().getId(), hotel.get().getUser().getId())&& !oUser.get().getRole().getName().equals("ADMIN")){
            throw new AppException("You Can't perform this action", HttpStatus.BAD_REQUEST);
        }

        if(!room.get().reservations.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(405), "You cant delete this room there are reservations for it");
        roomRepository.delete(room.get());
        return ResponseEntity.ok(room.get().id);
    }
}
