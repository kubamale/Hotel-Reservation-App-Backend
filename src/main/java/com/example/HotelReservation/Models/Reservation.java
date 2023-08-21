package com.example.HotelReservation.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;

    @Column
    public String firstName;
    @Column
    public String lastName;
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
    @JsonBackReference
    private Room room;


    public Reservation(String firstName, String lastName, Date startDate, Date endDate, int reservationNumber, String email, Room room) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationNumber = reservationNumber;
        this.email = email;
        this.room = room;
    }

    public Reservation() {
    }

    public Room getRoom() {
        return room;
    }
}
