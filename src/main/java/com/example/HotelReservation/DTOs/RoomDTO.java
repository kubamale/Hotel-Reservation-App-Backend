package com.example.HotelReservation.DTOs;
import java.util.List;

public class RoomDTO {
    public List<String> equipment;
    public double price;
    public Long hotelId;
    public int capacity;

    public RoomDTO() {
    }

    public RoomDTO(List<String> equipment, double price, Long hotelId, int capacity) {
        this.equipment = equipment;
        this.price = price;
        this.hotelId = hotelId;
        this.capacity = capacity;
    }
}
