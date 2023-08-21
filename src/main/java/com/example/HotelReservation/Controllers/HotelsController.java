package com.example.HotelReservation.Controllers;

import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.DTOs.HotelDTO;
import com.example.HotelReservation.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RequestMapping("/hotels")
@RestController
public class HotelsController {

    @Autowired
    HotelService hotelService;
    @GetMapping
    public List<HotelDTO> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping("/date")
    public List<HotelDTO> getHotelsBetweenDates(@RequestParam String startDate, @RequestParam String endDate){
        System.out.println("==================================\n " + startDate + " | " + endDate);
        return hotelService.GetAvailableHotels(Date.valueOf(startDate), Date.valueOf(endDate));
    }

    @PostMapping
    public Hotel addHotel(@RequestBody HotelDTO hotelDTO){
        return hotelService.createNewHotel(hotelDTO);
    }

    @DeleteMapping
    public String deleteHotel(@RequestParam Long hotelId){
        return hotelService.deleteHotel(hotelId);
    }

}
