package com.example.HotelReservation.DTOs;

import com.example.HotelReservation.Models.Amenities;
import com.example.HotelReservation.Models.Ratings;

import java.util.List;
import java.util.Set;

public class HotelGetDTO {
    public long id;
    public String country;
    public String city;
    public String postalCode;
    public String street;
    public String streetNumber;
    public String phoneNumber;
    public String email;
    public String name;
    public String description;
    public List<String> picURL;
    public Set<AmenitiesDTO> amenities;
    public Set<RatingsDTO> ratings;

    public HotelGetDTO() {
    }

    public HotelGetDTO(long id, String country, String city, String postalCode, String street, String streetNumber, String phoneNumber, String email, String name, String description, List<String> picURL, Set<AmenitiesDTO> amenities, Set<RatingsDTO> ratings) {
        this.id = id;
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
        this.ratings = ratings;
    }
}
