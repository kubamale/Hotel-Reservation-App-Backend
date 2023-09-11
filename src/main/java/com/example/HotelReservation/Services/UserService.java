package com.example.HotelReservation.Services;

import com.example.HotelReservation.DTOs.CredentialsDTO;
import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Exceptions.AppException;
import com.example.HotelReservation.Mappers.UserMapper;
import com.example.HotelReservation.Models.User;
import com.example.HotelReservation.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO login(CredentialsDTO credentialsDTO) throws RuntimeException {
        User user = userRepository.findByLogin(credentialsDTO.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()), user.getToken())){
            return userMapper.toUserDTO(user);

        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }
}
