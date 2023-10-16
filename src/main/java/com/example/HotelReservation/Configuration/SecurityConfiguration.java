package com.example.HotelReservation.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(req ->
                    req
                            .requestMatchers("/login", "/register")
                   .permitAll()
                            .requestMatchers(HttpMethod.GET, "/hotels", "/hotels/date", "/hotels/details", "/hotels/amenities", "/rooms").permitAll()
                            .requestMatchers(HttpMethod.POST, "/hotels", "/rooms").hasAnyRole("ADMIN", "HOTEL_OWNER")
                            .requestMatchers(HttpMethod.DELETE, "/hotels", "/rooms").hasAnyRole("ADMIN", "HOTEL_OWNER")
                            .requestMatchers(HttpMethod.PUT, "/hotels").hasAnyRole("ADMIN", "HOTEL_OWNER", "USER")
                            .requestMatchers("/users").hasAnyRole("ADMIN", "HOTEL_OWNER", "USER")
                            .requestMatchers("/reservation").hasAnyRole("ADMIN", "HOTEL_OWNER", "USER")

                   .anyRequest().authenticated()
               )
               .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider)
               .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
