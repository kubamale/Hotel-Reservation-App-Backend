package com.example.HotelReservation.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Tokens_Black_List")
@NoArgsConstructor
public class BlackListToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true)
    String token;

    public BlackListToken(String token) {
        this.token = token;
    }
}
