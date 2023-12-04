package com.example.cinema_backend.controllers;

import com.example.cinema_backend.models.Movie;
import com.example.cinema_backend.repositories.MovieRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping("/api")
public class MovieController {

    private MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public ResponseEntity<String> getMovies() {
        Collection<Movie> movies = movieRepository.findAll();
        return ResponseEntity.status(200).body(toJson(movies));
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
