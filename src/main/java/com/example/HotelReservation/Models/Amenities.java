package com.example.HotelReservation.Models;

import com.example.HotelReservation.DTOs.AmenitiesDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "Amenities")
@NoArgsConstructor
@AllArgsConstructor
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String path;

    @ManyToMany(mappedBy = "amenities")
    Set<Hotel> hotels;

    public Amenities(String name, String path){
        this.name = name;
        this.path = path;
    }

    public static AmenitiesDTO mapToDTO(Amenities amenities){
        return new AmenitiesDTO(amenities.id, amenities.name, amenities.path);
    }
}
