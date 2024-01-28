package com.example.cinema_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User (String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public User (String email, String password, String firstName, String lastName, String role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = Role.valueOf(role);
    }
}
