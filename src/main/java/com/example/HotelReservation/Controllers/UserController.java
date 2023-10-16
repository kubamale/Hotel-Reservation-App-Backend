package com.example.HotelReservation.Controllers;

import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUserDetails(@RequestParam long id){
        return userService.getUserDetails(id);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAccount(@RequestParam Long userId){
        return userService.deleteAccount(userId);
    }

}
