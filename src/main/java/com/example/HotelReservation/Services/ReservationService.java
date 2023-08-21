package com.example.HotelReservation.Services;

import com.example.HotelReservation.DTOs.InsertReservationDTO;
import com.example.HotelReservation.Email.EmailService;
import com.example.HotelReservation.Models.Reservation;
import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Repositories.ReservationRepository;
import com.example.HotelReservation.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;

@Service
public class ReservationService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    EmailService emailService;
    public Reservation createReservation(InsertReservationDTO reservationDTO) {

        Optional<Room> room = roomRepository.findById(reservationDTO.roomId);
        if (room.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "No room with that Id");


        Reservation reservation = new Reservation(reservationDTO.firstName, reservationDTO.lastName, reservationDTO.startDate, reservationDTO.endDate, generateReservationNumber(), reservationDTO.email, room.get());
        reservationRepository.save(reservation);
        emailService.notifyAboutPlacingReservation(reservation);
        return reservation;
    }

    private int generateReservationNumber(){
        Random random = new Random();

        int minRange = 1000000;
        int maxRange = 9999999;

        return random.nextInt(maxRange - minRange + 1) + minRange;
    }

    public int deleteReservation(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "No reservation with that Id");

        reservationRepository.delete(reservation.get());
        emailService.notifyAboutCancellingReservation(reservation.get());
        return reservation.get().reservationNumber;
    }
}
