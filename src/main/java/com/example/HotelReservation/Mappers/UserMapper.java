package com.example.HotelReservation.Mappers;

import com.example.HotelReservation.DTOs.SignUpDTO;
import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    User signUpToUser(SignUpDTO signUpDTO);
}
