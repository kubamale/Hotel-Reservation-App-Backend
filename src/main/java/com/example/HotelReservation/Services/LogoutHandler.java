package com.example.HotelReservation.Services;

import com.example.HotelReservation.Models.BlackListToken;
import com.example.HotelReservation.Repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {


    private final TokenRepository tokenRepository;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null){
            String[] data = header.split(" ");
            if (data.length == 2 && data[0].equals("Bearer")){
                tokenRepository.save(new BlackListToken(data[1]));
            }
        }
    }
}
