package com.example.HotelReservation.Mappers;

import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);
}
