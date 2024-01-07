package com.example.cinema_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NonNull
    @Column(name="email")
    private String email;

    @NonNull
    @Column(name="password")
    private String password;

    @NonNull
    @Column(name="first_name")
    private String firstName;

    @NonNull
    @Column(name="last_name")
    private String lastName;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User (String email, String password) {
        this.email = email;
        this.password = password;
    }
}
