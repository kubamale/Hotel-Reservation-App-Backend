package com.example.HotelReservation.Controllers;

import com.example.HotelReservation.DTOs.CredentialsDTO;
import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {



    @Autowired
    private UserService userService;

    @PostMapping("/login")
    ResponseEntity<UserDTO> login(@RequestBody CredentialsDTO credentialsDTO){
        UserDTO user =  userService.login(credentialsDTO);
        return ResponseEntity.ok(user);
    }
}
