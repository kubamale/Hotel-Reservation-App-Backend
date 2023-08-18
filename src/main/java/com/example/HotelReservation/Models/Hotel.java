package com.example.HotelReservation.Models;

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
    private Set<Room> rooms;

    public Hotel(String name, String description, List<String> picURL, List<String> amenities) {

        this.name = name;
        this.description = description;
        this.picURL = picURL;
        this.amenities = amenities;
        this.ratings = ratings;
    }

    public Hotel() {

    }

    public Set<Room> getRooms() {
        return rooms;
    }
}
