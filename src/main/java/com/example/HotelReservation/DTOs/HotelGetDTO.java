package com.example.HotelReservation.DTOs;

import java.util.List;

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
    public List<String> amenities;

    public HotelGetDTO() {
    }

    public HotelGetDTO(long id, String country, String city, String postalCode, String street, String streetNumber, String phoneNumber, String email, String name, String description, List<String> picURL, List<String> amenities) {
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
    }
}
