package com.example.HotelReservation.DTOs;

import jakarta.persistence.Column;

import java.util.Date;

public class InsertReservationDTO {
    public String firstName;
    public String lastName;
    public Date startDate;
    public Date endDate;
    public String email;
    public Long roomId;
}
