package com.example.HotelReservation.DTOs;

import java.util.Date;

public record RatingsDTO(int rating, String opinion, Date date, long hotelId, long userId) {

}
