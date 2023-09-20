package com.example.HotelReservation.Controllers;

import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUserDetails(@RequestParam long id){
        return userService.getUserDetails(id);
    }

}
