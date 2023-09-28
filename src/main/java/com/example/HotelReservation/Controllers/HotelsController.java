package com.example.HotelReservation.Controllers;

import com.example.HotelReservation.DTOs.AmenitiesDTO;
import com.example.HotelReservation.DTOs.HotelGetDTO;
import com.example.HotelReservation.DTOs.RatingsDTO;
import com.example.HotelReservation.Models.Amenities;
import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.DTOs.CreateHotelDTO;
import com.example.HotelReservation.Models.Ratings;
import com.example.HotelReservation.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RequestMapping("/hotels")
@RestController
public class HotelsController {

    @Autowired
    HotelService hotelService;
    @GetMapping
    public List<HotelGetDTO> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping("/date")
    public List<HotelGetDTO> getHotelsBetweenDates(@RequestParam String startDate, @RequestParam String endDate){
        System.out.println("==================================\n " + startDate + " | " + endDate);
        return hotelService.GetAvailableHotels(Date.valueOf(startDate), Date.valueOf(endDate));
    }

    @GetMapping("/details")
    public HotelGetDTO getHotel(@RequestParam Long id){
        return hotelService.getHotel(id);
    }

    @PostMapping
    public Hotel addHotel(@RequestBody CreateHotelDTO hotelDTO){
        return hotelService.createNewHotel(hotelDTO);
    }

    @DeleteMapping
    public String deleteHotel(@RequestParam Long hotelId){
        return hotelService.deleteHotel(hotelId);
    }

    @GetMapping("/user")
    public ResponseEntity<List<HotelGetDTO>> getUsersHotel(@RequestParam long id){
        return hotelService.getUsersHotels(id);
    }

    @GetMapping("amenities")
    public ResponseEntity<List<AmenitiesDTO>> getAllAmenities(){
        return hotelService.getAllAmenities();
    }

    @PutMapping("/ratings")
    public ResponseEntity<RatingsDTO> addOpinion(@RequestBody RatingsDTO ratingsDTO){
        return hotelService.addOpinion(ratingsDTO);
    }

}
