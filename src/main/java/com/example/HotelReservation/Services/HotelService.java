package com.example.HotelReservation.Services;

import com.example.HotelReservation.DTOs.AmenitiesDTO;
import com.example.HotelReservation.DTOs.CreateHotelDTO;
import com.example.HotelReservation.DTOs.HotelGetDTO;
import com.example.HotelReservation.DTOs.RatingsDTO;
import com.example.HotelReservation.Exceptions.AppException;
import com.example.HotelReservation.Models.*;
import com.example.HotelReservation.Repositories.AmenitiesReopsitory;
import com.example.HotelReservation.Repositories.HotelRepository;
import com.example.HotelReservation.Repositories.RatingsRepository;
import com.example.HotelReservation.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    AmenitiesReopsitory amenitiesReopsitory;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RatingsRepository ratingsRepository;

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

        Set<Amenities> amenities = new HashSet<>(amenitiesReopsitory.findAllById(hotelDTO.amenities.stream().map(AmenitiesDTO::id).collect(Collectors.toSet())));
        Hotel hotel = new Hotel(hotelDTO.country, hotelDTO.city, hotelDTO.postalCode, hotelDTO.street, hotelDTO.streetNumber, hotelDTO.phoneNumber, hotelDTO.email,hotelDTO.name, hotelDTO.description, hotelDTO.picURL, amenities, oUser.get());
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

    public ResponseEntity<List<HotelGetDTO>> getUsersHotels(long id) {
        List<Hotel> hotels = hotelRepository.findAll().stream().filter((hotel) -> hotel.getUser().getId() == id).toList();



        return ResponseEntity.ok(hotels.stream().map(Hotel::mapToDTO).toList());
    }

    public ResponseEntity<List<AmenitiesDTO>> getAllAmenities() {
        return ResponseEntity.ok(amenitiesReopsitory.findAll().stream().map(Amenities::mapToDTO).toList());
    }

    public ResponseEntity<RatingsDTO> addOpinion(RatingsDTO ratingsDTO) {
        System.out.println("==============" + ratingsDTO.hotelId() + " | " + ratingsDTO.userId());
        Optional<User> oUser = userRepository.findById(ratingsDTO.userId());
        Optional<Hotel> oHotel = hotelRepository.findById(ratingsDTO.hotelId());

        if (oUser.isEmpty() || oHotel.isEmpty()){
            throw new AppException("Wrong hotel or user", HttpStatus.BAD_REQUEST);
        }

        Ratings ratings = new Ratings(ratingsDTO.rating(), ratingsDTO.opinion(), ratingsDTO.date(), oHotel.get(), oUser.get());
        ratingsRepository.save(ratings);

        return ResponseEntity.ok(Ratings.mapToDTO(ratings));
    }
}
