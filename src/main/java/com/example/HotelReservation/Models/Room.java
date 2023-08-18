package com.example.HotelReservation.Models;

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

    @OneToMany(mappedBy = "room")
    public Set<Reservation> reservations;
    @ManyToOne
    @JoinColumn(name = "Hotel_Id")
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
            if ((res.startDate.after(startDate) && res.startDate.before(endDate)) || (res.endDate.after(startDate) && res.endDate.before(endDate)))
                return false;
        }
        return true;
    }


}
