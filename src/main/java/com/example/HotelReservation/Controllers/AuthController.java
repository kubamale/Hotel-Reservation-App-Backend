package com.example.HotelReservation.Controllers;

import com.example.HotelReservation.DTOs.CredentialsDTO;
import com.example.HotelReservation.DTOs.SignUpDTO;
import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    ResponseEntity<UserDTO> login(@RequestBody CredentialsDTO credentialsDTO){
        UserDTO user =  userService.login(credentialsDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody SignUpDTO signUpDTO){
        UserDTO user = userService.register(signUpDTO);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

}
