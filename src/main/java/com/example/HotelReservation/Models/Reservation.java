package com.example.HotelReservation.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;

    @Column
    public Date startDate;
    @Column
    public Date endDate;
    @Column
    public int reservationNumber;
    @Column
    public String email;

    @ManyToOne
    @JoinColumn(name = "Room_Id")
    private Room room;

    public Reservation(Date startDate, Date endDate, int reservationNumber, String email, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationNumber = reservationNumber;
        this.email = email;
        this.room = room;
    }

    public Reservation() {
    }
}
