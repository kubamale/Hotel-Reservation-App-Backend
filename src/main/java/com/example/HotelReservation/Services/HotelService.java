package com.example.HotelReservation.Services;

import com.example.HotelReservation.DTOs.CreateHotelDTO;
import com.example.HotelReservation.DTOs.HotelGetDTO;
import com.example.HotelReservation.Exceptions.AppException;
import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.Models.Reservation;
import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Models.User;
import com.example.HotelReservation.Repositories.HotelRepository;
import com.example.HotelReservation.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    UserRepository userRepository;

    public List<HotelGetDTO> getAllHotels() {
        return hotelRepository.findAll().stream().map(Hotel::mapToDTO).toList();
    }

    public List<HotelGetDTO> GetAvailableHotels(Date startDate, Date endDate) {
        List<Hotel> allHotels = hotelRepository.findAll();
        List<Hotel> availableHotels = new ArrayList<>();
        for (Hotel hotel : allHotels) {
            boolean canAddHotel = true;
            for (Room room : hotel.getRooms()){
                if (!room.isRoomAvailable(startDate, endDate)) {
                    canAddHotel = false;

                    break;
                }
            }
            if (canAddHotel)
                availableHotels.add(hotel);
        }

        return availableHotels.stream().map(Hotel::mapToDTO).toList();
    }




    public Hotel createNewHotel(CreateHotelDTO hotelDTO) {
        Optional<User> oUser = userRepository.findById(hotelDTO.userId);

        if (oUser.isEmpty()){
            throw new AppException("No user with that Id " + hotelDTO.userId, HttpStatus.BAD_REQUEST);
        }

        Hotel hotel = new Hotel(hotelDTO.country, hotelDTO.city, hotelDTO.postalCode, hotelDTO.street, hotelDTO.streetNumber, hotelDTO.phoneNumber, hotelDTO.email,hotelDTO.name, hotelDTO.description, hotelDTO.picURL, hotelDTO.amenities, oUser.get());
        hotelRepository.save(hotel);
        return hotel;
    }

    public String deleteHotel(Long hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);

        if(hotel.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "no hotel With that Id");
        List<Reservation> reservationsToDelete = hotel.get().getRooms().stream().flatMap(room -> room.reservations.stream()).toList();
        boolean hasReservation = false;
        for (Room room : hotel.get().getRooms()){
            if (!room.reservations.isEmpty()){
                hasReservation = true;
                break;
            }

        }

        if (hasReservation)
            throw new ResponseStatusException(HttpStatusCode.valueOf(405), "You cant delete this hotel there are reservations for it");

        hotelRepository.delete(hotel.get());

        return hotel.get().name;

    }

    public HotelGetDTO getHotel(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "no hotel With that Id");

        return Hotel.mapToDTO(hotel.get());
    }
}
