package com.example.HotelReservation.Models;

import com.example.HotelReservation.DTOs.HotelDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Hotel")
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
    @Column
    public List<String> amenities;
    @Column
    public int ratings;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    public Set<Room> rooms;

    public Hotel(String country, String city, String postalCode, String street, String streetNumber, String phoneNumber, String email, String name, String description, List<String> picURL, List<String> amenities) {
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
    }

    public Hotel() {

    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public static HotelDTO mapToDTO(Hotel hotel){
        return new HotelDTO(hotel.country, hotel.city, hotel.postalCode,
                hotel.street, hotel.streetNumber, hotel.phoneNumber, hotel.email,
                hotel.name, hotel.description, hotel.picURL, hotel.amenities);
    }
}
