package com.example.HotelReservation.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.HotelReservation.Configuration.UserAuthProvider;
import com.example.HotelReservation.DTOs.CredentialsDTO;
import com.example.HotelReservation.DTOs.SignUpDTO;
import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Exceptions.AppException;
import com.example.HotelReservation.Mappers.UserMapper;
import com.example.HotelReservation.Models.BlackListToken;
import com.example.HotelReservation.Models.User;
import com.example.HotelReservation.Repositories.TokenRepository;
import com.example.HotelReservation.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAuthProvider userAuthProvider;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO login(CredentialsDTO credentialsDTO) throws RuntimeException {
        User user = userRepository.findByLogin(credentialsDTO.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.UNAUTHORIZED));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()), user.getPassword())){
            return userMapper.toUserDTO(user);

        }

        throw new AppException("Invalid password", HttpStatus.UNAUTHORIZED);
    }

    public UserDTO register(SignUpDTO signUpDTO) {
        Optional<User> oUser = userRepository.findByLogin(signUpDTO.login());
        if(oUser.isPresent()){
            throw  new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDTO);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.password())));
        User savedUser = userRepository.save(user);

        return userMapper.toUserDTO(savedUser);
    }

    public ResponseEntity<String> logout(String token) {

        tokenRepository.save(new BlackListToken(token));
        return ResponseEntity.ok("Logged out");
    }

}
