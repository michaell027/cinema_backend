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

import java.util.HashMap;
import java.util.Map;


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
        Gson gson = new Gson();

        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser == null) {
            return ResponseEntity.status(404).body(gson.toJson("User not found"));
        } else if (!isPasswordValid(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.status(401).body(gson.toJson("Invalid password"));
        }
//        else if (tokenService.hasToken(foundUser.getId())) {
//            return ResponseEntity.status(402).body(gson.toJson("User already logged in"));
//        }
        else {
            String token = tokenGenerator.generateToken();
            tokenService.storeToken(foundUser.getId(), token);
            Map<String, String> jsonResponse = new HashMap<>();

            jsonResponse.put("token", token);
            jsonResponse.put("username", foundUser.getFirstName());
            jsonResponse.put("role", foundUser.getRole().toString());
            return ResponseEntity.status(200).body(gson.toJson(jsonResponse));
        }
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private boolean isPasswordValid(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }
}