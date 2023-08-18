package com.example.HotelReservation.Services;

import com.example.HotelReservation.DTOs.InsertHotelDTO;
import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;

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
}
