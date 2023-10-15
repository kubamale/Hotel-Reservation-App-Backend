package com.example.HotelReservation.DTOs;

import com.example.HotelReservation.Models.Role;

public record SignUpDTO(String firstName, String lastName, String login, Role role, char[] password) {
}
