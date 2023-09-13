package com.example.HotelReservation.Repositories;


import com.example.HotelReservation.Models.BlackListToken;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TokenRepository extends JpaRepository<BlackListToken, String> {

    Optional<BlackListToken> getBlackListedTokenByToken(String token);

}
