package com.example.HotelReservation.Models;

import com.example.HotelReservation.DTOs.CreateHotelDTO;
import com.example.HotelReservation.DTOs.HotelGetDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "Hotel")
@Getter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;
    @Column
    public String country;
    @Column
    public String city;
    @Column
    public String postalCode;
    @Column
    public String street;
    @Column
    public String streetNumber;
    @Column
    public String phoneNumber;
    @Column
    public String email;
    @Column
    public String name;
    @Column
    @Lob
    public String description;
    @Column
    public List<String> picURL;
    @ManyToMany
    public Set<Amenities> amenities;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    public Set<Room> rooms;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    public Set<Ratings> ratings;
    @ManyToOne
    @JoinColumn(name = "User_Id")
    @JsonBackReference
    private User user;

    public Hotel(String country, String city, String postalCode, String street, String streetNumber, String phoneNumber, String email, String name, String description, List<String> picURL, Set<Amenities> amenities, User user) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.name = name;
        this.description = description;
        this.picURL = picURL;
        this.amenities = amenities;
        this.user = user;
    }

    public Hotel() {

    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public static HotelGetDTO mapToDTO(Hotel hotel){
        return new HotelGetDTO(hotel.Id,hotel.country, hotel.city, hotel.postalCode,
                hotel.street, hotel.streetNumber, hotel.phoneNumber, hotel.email,
                hotel.name, hotel.description, hotel.picURL, hotel.amenities.stream().map(Amenities::mapToDTO).collect(Collectors.toSet()), hotel.ratings.stream().map(Ratings::mapToDTO).collect(Collectors.toSet()));
    }
}
