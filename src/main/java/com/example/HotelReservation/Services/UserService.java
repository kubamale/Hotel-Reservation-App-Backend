package com.example.HotelReservation.Services;

import com.example.HotelReservation.Configuration.JwtService;
import com.example.HotelReservation.DTOs.CredentialsDTO;
import com.example.HotelReservation.DTOs.SignUpDTO;
import com.example.HotelReservation.DTOs.UserDTO;
import com.example.HotelReservation.Email.EmailService;
import com.example.HotelReservation.Exceptions.AppException;
import com.example.HotelReservation.Mappers.UserMapper;
import com.example.HotelReservation.Models.BlackListToken;
import com.example.HotelReservation.Models.Role;
import com.example.HotelReservation.Models.User;
import com.example.HotelReservation.Repositories.RoleRepository;
import com.example.HotelReservation.Repositories.TokenRepository;
import com.example.HotelReservation.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public UserDTO login(CredentialsDTO credentialsDTO) throws RuntimeException {
        User user = userRepository.findByLogin(credentialsDTO.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.UNAUTHORIZED));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()), user.getPassword())){
            return UserDTO.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .login(user.getLogin())
                    .id(user.getId())
                    .token(jwtService.generateToken(user)).build();

        }

        throw new AppException("Invalid password", HttpStatus.UNAUTHORIZED);
    }

    public UserDTO register(SignUpDTO signUpDTO) {
        Optional<User> oUser = userRepository.findByLogin(signUpDTO.login());
        if(oUser.isPresent()){
            throw  new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        Optional<Role> role = roleRepository.findByName(signUpDTO.roleName());

        if (role.isEmpty()){
            throw new AppException("No role with name " + signUpDTO.roleName(), HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDTO);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.password())));
        user.setRole(role.get());
        User savedUser = userRepository.save(user);
        emailService.sendWelcomeEmail(savedUser);
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .id(user.getId())
                .token(jwtService.generateToken(user)).build();
    }

    public ResponseEntity<String> logout(String token) {

        tokenRepository.save(new BlackListToken(token));
        return ResponseEntity.ok("Logged out");
    }


    public ResponseEntity<UserDTO> getUserDetails(long id) {

        Optional<User> oUser = userRepository.findById(id);

        if (oUser.isEmpty())
            throw new AppException("no user with id " + id, HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(userMapper.toUserDTO(oUser.get()));
    }

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll().stream().map( user -> UserDTO.builder()
                        .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .build()).toList());
    }


    public ResponseEntity<String> deleteAccount(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            Optional<User> oUser = userRepository.findById(userId);
            Optional<User> invoker = userRepository.findByLogin(currentUserName);

            if (oUser.isEmpty() || invoker.isEmpty()){
                return ResponseEntity.badRequest().body("No user with that id");
            }

            if (oUser.get().getLogin().equals(currentUserName) || invoker.get().getRole().getName().equals("ADMIN")){
                userRepository.delete(oUser.get());
                return ResponseEntity.ok("Deleted user: " + oUser.get().getLogin());
            }


        }

        return ResponseEntity.badRequest().body("You are not authorized to perform that action");
    }
}
