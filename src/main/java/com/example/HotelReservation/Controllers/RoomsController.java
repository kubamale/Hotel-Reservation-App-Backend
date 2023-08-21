package com.example.HotelReservation.Controllers;

import com.example.HotelReservation.DTOs.RoomDTO;
import com.example.HotelReservation.Models.Room;
import com.example.HotelReservation.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RequestMapping("/rooms")
@RestController()
public class RoomsController {

    @Autowired
    RoomService roomService;

    @GetMapping()
    public List<RoomDTO> getRooms(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Long id){
        return roomService.getRoomsForHotel(id, Date.valueOf(startDate), Date.valueOf(endDate));

    }

    @PostMapping
    public Room addRoom(@RequestBody RoomDTO roomDTO){
        return roomService.createNewRoom(roomDTO);
    }

    @DeleteMapping
    public Long deleteRoom(@RequestParam Long roomId){
        return roomService.deleteRoom(roomId);
    }
}
