package com.example.HotelReservation.Services;

import com.example.HotelReservation.DTOs.InsertHotelDTO;
import com.example.HotelReservation.Email.EmailService;
import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.Models.Reservation;
import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    EmailService emailService;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public List<Hotel> GetAvailableHotels(Date startDate, Date endDate) {
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

        return availableHotels;
    }


    public Hotel createNewHotel(InsertHotelDTO hotelDTO) {
        Hotel hotel = new Hotel(hotelDTO.name, hotelDTO.description, hotelDTO.picURL, hotelDTO.amenities);
        hotelRepository.save(hotel);
        return hotel;
    }

    public String deleteHotel(Long hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);

        if(hotel.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "no hotel With that Id");
        List<Reservation> reservationsToDelete = hotel.get().getRooms().stream().flatMap(room -> room.reservations.stream()).toList();

        hotelRepository.delete(hotel.get());
        emailService.sendSimpleEmail("kubamalewicztest@gmail.com", "test", "test text");

        return hotel.get().name;

    }
}
