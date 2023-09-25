package com.example.HotelReservation.Models;

import com.example.HotelReservation.DTOs.RatingsDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Ratings")
@NoArgsConstructor
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column
    int rating;

    @Column
    String opinion;
    @Column
    Date date;
    @ManyToOne
    @JoinColumn(name = "Hotel_Id")
    @JsonBackReference
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    @JsonBackReference
    private User user;

    public Ratings(int rating, String opinion, Date date, Hotel hotel, User user) {
        this.rating = rating;
        this.opinion = opinion;
        this.date = date;
        this.hotel = hotel;
        this.user = user;
    }

    public static RatingsDTO mapToDTO(Ratings ratings){
        return new RatingsDTO(ratings.rating, ratings.opinion, ratings.date, ratings.hotel.Id, ratings.user.getId());
    }
}
