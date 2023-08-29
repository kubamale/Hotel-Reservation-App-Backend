package com.example.HotelReservation.Models;

import com.example.HotelReservation.DTOs.RoomDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column
    public List<String> equipment;

    @Column
    public double price;
    @Column
    public int capacity;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    public Set<Reservation> reservations;
    @ManyToOne
    @JoinColumn(name = "Hotel_Id")
    @JsonBackReference
    private Hotel hotel;

    public Room(List<String> equipment, double price, Hotel hotel, int capacity) {
        this.equipment = equipment;
        this.price = price;
        this.hotel = hotel;
        this.capacity = capacity;
    }

    public Room() {
    }

    public boolean isRoomAvailable(Date startDate, Date endDate){
        for (Reservation res: reservations) {
            System.out.println("======================= " + res.reservationNumber);
            if ((startDate.after(res.startDate) && startDate.before(res.endDate)) || (endDate.after(res.startDate) && endDate.before(res.endDate)))
                return false;
        }
        return true;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public static RoomDTO mapToDTO(Room room){
        return new RoomDTO(room.equipment, room.price, room.hotel.Id, room.capacity);
    }
}
