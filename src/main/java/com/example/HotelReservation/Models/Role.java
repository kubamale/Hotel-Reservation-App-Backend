package com.example.HotelReservation.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;



@NoArgsConstructor
@Entity
@Table(name = "_role")
public class Role {

    @Id
    @GeneratedValue
    private Long Id;
    @Getter
    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public List<SimpleGrantedAuthority> getAuthorities(){

        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name));
    }

    public Role(String name) {
        this.name = name;
    }
}
