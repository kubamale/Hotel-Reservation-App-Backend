package com.example.HotelReservation.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Exceptions.AppException;
import com.example.HotelReservation.Models.BlackListToken;
import com.example.HotelReservation.Repositories.TokenRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    private final TokenRepository tokenRepository;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String CreateToken(UserDTO userDTO){
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withIssuer(userDTO.getLogin())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstName", userDTO.getFirstName())
                .withClaim("lastName", userDTO.getLastName())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token){

        if (isTokenDisabled(token)){
            throw new AppException("Your Session has expired please Log in", HttpStatus.UNAUTHORIZED);
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        UserDTO user = UserDTO.builder().login(decoded.getIssuer())
                .firstName(decoded.getClaim("firstName").asString())
                .lastName(decoded.getClaim("lastName").asString())
                .build();


        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

    }

    private boolean isTokenDisabled(String token){
        Optional<BlackListToken> oToken = tokenRepository.getBlackListedTokenByToken(token);

        return oToken.isPresent();
    }
}
