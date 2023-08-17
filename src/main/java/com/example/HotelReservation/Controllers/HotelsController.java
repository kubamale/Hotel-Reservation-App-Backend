package com.example.HotelReservation.Controllers;

import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.Repositories.HotelRepository;
import com.example.HotelReservation.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RequestMapping("/hotels")
@RestController
public class HotelsController {

    @Autowired
    HotelService hotelService;
    @GetMapping
    public List<Hotel> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping("/date")
    public List<Hotel> getHotelsBetweenDates(@RequestParam String startDate, @RequestParam String endDate){
        System.out.println("==================================\n " + startDate + " | " + endDate);
        return hotelService.GetAvailableHotels(Date.valueOf(startDate), Date.valueOf(endDate));
    }

}
