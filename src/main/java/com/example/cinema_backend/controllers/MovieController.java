package com.example.cinema_backend.controllers;

import com.example.cinema_backend.models.Movie;
import com.example.cinema_backend.repositories.MovieRepository;
import com.example.cinema_backend.repositories.UserRepository;
import com.example.cinema_backend.services.TokenService;
import com.example.cinema_backend.utils.MovieUtils;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private MovieRepository movieRepository;

    private MovieUtils movieUtils = new MovieUtils();

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private TokenService tokenService = new TokenService();

    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<String> getMovies() {
        Collection<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            return ResponseEntity.status(404).body(movieUtils.toJson("No movies found"));
        }
        return ResponseEntity.status(200).body(movieUtils.toJson(movies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getMovieById(long id) {
        Movie movie = movieRepository.findById(id);
        if (movie == null) {
            return ResponseEntity.status(404).body(("No movie found"));
        }
        return ResponseEntity.status(200).body(movieUtils.toJson(movie));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@RequestHeader String token, @RequestBody Movie movie) {
        if (token.isEmpty() || token.isBlank() || token == null) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }
        Long userId = tokenService.getUserId(token);
        if (userId == null) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }

        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            return ResponseEntity.status(409).body(movieUtils.toJson("Movie already exists"));
        } else if (movie.getTitle().isEmpty() || movie.getDescription().isEmpty() || movie.getDuration().isEmpty() || movie.getGenre().isEmpty() || movie.getReleaseDate().isEmpty()) {
            return ResponseEntity.status(400).body(movieUtils.toJson("Missing movie information"));
        }
        movieRepository.save(movie);
        Gson gson = new Gson();
        return ResponseEntity.status(200).body(gson.toJson("Movie added"));
    }
}
