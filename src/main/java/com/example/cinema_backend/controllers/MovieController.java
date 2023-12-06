package com.example.cinema_backend.controllers;

import com.example.cinema_backend.models.Movie;
import com.example.cinema_backend.repositories.MovieRepository;
import com.example.cinema_backend.utils.MovieUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping("/api")
public class MovieController {

    private MovieRepository movieRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private MovieUtils movieUtils = new MovieUtils();

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public ResponseEntity<String> getMovies() {
        Collection<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            return ResponseEntity.status(404).body(movieUtils.toJson("No movies found"));
        }
        return ResponseEntity.status(200).body(movieUtils.toJson(movies));
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<String> getMovieById(long id) {
        Movie movie = movieRepository.findById(id);
        if (movie == null) {
            return ResponseEntity.status(404).body(("No movie found"));
        }
        return ResponseEntity.status(200).body(movieUtils.toJson(movie));
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private boolean isPasswordValid(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }
}
