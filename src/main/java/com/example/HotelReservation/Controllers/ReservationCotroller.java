package com.example.HotelReservation.Controllers;

import com.example.HotelReservation.DTOs.InsertReservationDTO;
import com.example.HotelReservation.Models.Reservation;
import com.example.HotelReservation.Services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/reservation")
@RestController
public class ReservationCotroller {

    @Autowired
    ReservationService reservationService;

    @PostMapping
    public Reservation addReservation(@RequestBody InsertReservationDTO reservationDTO){
        return reservationService.createReservation(reservationDTO);
    }

}
