package com.example.HotelReservation.Models;

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
    public String description;
    @Column
    public List<String> picURL;
    @Column
    public List<String> amenities;
    @Column
    public int ratings;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Room> rooms;

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
}
