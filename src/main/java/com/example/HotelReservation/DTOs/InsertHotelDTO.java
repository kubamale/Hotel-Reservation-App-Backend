package com.example.HotelReservation.DTOs;

import jakarta.persistence.Column;

import java.util.List;

public class InsertHotelDTO {
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
}
