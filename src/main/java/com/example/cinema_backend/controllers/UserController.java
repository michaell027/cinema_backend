package com.example.cinema_backend.controllers;

import com.example.cinema_backend.models.User;
import com.example.cinema_backend.repositories.UserRepository;
import com.example.cinema_backend.services.TokenService;
import com.example.cinema_backend.utils.TokenGenerator;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;
    private TokenGenerator tokenGenerator = new TokenGenerator();
    private TokenService tokenService = new TokenService();

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        System.out.println(user.getEmail());
        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser != null) {
            return ResponseEntity.status(409).body("User already exists");
        } else {
            user.setPassword(encodePassword(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.status(201).body("User created");
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser == null) {
            return ResponseEntity.status(404).body("User not found");
        } else if (!isPasswordValid(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        } else {
            String token = tokenGenerator.generateToken();
            tokenService.storeToken(foundUser.getId(), token);
            Gson gson = new Gson();
            String json = gson.toJson("Authorization: " + token);
            return ResponseEntity.status(200).body(json);
        }
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private boolean isPasswordValid(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }
}