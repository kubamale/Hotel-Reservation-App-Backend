package com.example.HotelReservation.Configuration;

import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.Models.Reservation;
import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Repositories.HotelRepository;
import com.example.HotelReservation.Repositories.ReservationRepository;
import com.example.HotelReservation.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {


        Hotel hotel1 = new Hotel("test1", "test1 des1", new ArrayList<>(Arrays.asList("element1", "element2", "element3")),new ArrayList<>(Arrays.asList("element1", "element2", "element3")));
        Hotel hotel2 = new Hotel("test2", "test2 des2", new ArrayList<>(Arrays.asList("element1", "element2", "element3")),new ArrayList<>(Arrays.asList("element1", "element2", "element3")));

        Room room1 = new Room( new ArrayList<>(Arrays.asList("equipment1", "equipment2", "equipment3")), 123.00, hotel1, 3);
        Room room2 = new Room( new ArrayList<>(Arrays.asList("equipment1", "equipment3")), 163.00, hotel2, 5);
        Reservation reservation = new Reservation("John", "Doe",Date.valueOf("2022-02-05"),Date.valueOf("2022-04-12"), 1232131, "test", room1);
        hotelRepository.save(hotel1);
        hotelRepository.save(hotel2);
        roomRepository.save(room1);
        roomRepository.save(room2);
        reservationRepository.save(reservation);

    }
}
