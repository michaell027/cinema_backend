package com.example.cinema_backend.controllers;

import com.example.cinema_backend.models.Movie;
import com.example.cinema_backend.models.MovieSession;
import com.example.cinema_backend.repositories.MovieRepository;
import com.example.cinema_backend.repositories.MovieSessionRepository;
import com.example.cinema_backend.repositories.UserRepository;
import com.example.cinema_backend.services.TokenService;
import com.example.cinema_backend.utils.MovieUtils;
import com.google.gson.Gson;
import io.swagger.v3.core.util.Json;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private MovieRepository movieRepository;

    private MovieSessionRepository movieSessionRepository;

    private MovieUtils movieUtils = new MovieUtils();

    private UserRepository userRepository;


    public MovieController(MovieRepository movieRepository, MovieSessionRepository movieSessionRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.movieSessionRepository = movieSessionRepository;
        this.movieRepository = movieRepository;
    }

    private TokenService tokenService = TokenService.getInstance();


    @GetMapping("/all")
    public ResponseEntity<String> getMovies(@RequestHeader("Authorization") String token) {
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }
        Collection<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            return ResponseEntity.status(404).body(movieUtils.toJson("No movies found"));
        }
        System.out.println(movies);
        return ResponseEntity.status(200).body(movieUtils.toJson(movies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getMovieById(@PathVariable int id) {
        Movie movie = movieRepository.findById(id);
        if (movie == null) {
            return ResponseEntity.status(404).body(("No movie found"));
        }
        return ResponseEntity.status(200).body(movieUtils.toJson(movie));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@RequestHeader("Authorization") String token, @RequestBody Movie movie) {
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }
        if (!tokenService.isAdmin(token, userRepository)) {
            return ResponseEntity.status(403).body(movieUtils.toJson("Forbidden"));
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

    @PutMapping("/update/{id}")
    public ResponseEntity<String> editMovie(@PathVariable int id, @RequestBody Movie movie, @RequestHeader("Authorization") String token) {
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }
        if (!tokenService.isAdmin(token, userRepository)) {
            return ResponseEntity.status(403).body(movieUtils.toJson("Forbidden"));
        }
        Movie foundMovie = movieRepository.findById(id);
        if (foundMovie == null) {
            return ResponseEntity.status(404).body(movieUtils.toJson("Movie not found"));
        } else if (movie.getTitle().isEmpty() || movie.getDescription().isEmpty() || movie.getDuration().isEmpty() || movie.getGenre().isEmpty() || movie.getReleaseDate().isEmpty()) {
            return ResponseEntity.status(400).body(movieUtils.toJson("Missing movie information"));
        }
        foundMovie.setTitle(movie.getTitle());
        foundMovie.setDescription(movie.getDescription());
        foundMovie.setDuration(movie.getDuration());
        foundMovie.setGenre(movie.getGenre());
        foundMovie.setReleaseDate(movie.getReleaseDate());
        movieRepository.save(foundMovie);
        return ResponseEntity.status(200).body(movieUtils.toJson("Movie updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }
        if (!tokenService.isAdmin(token, userRepository)) {
            return ResponseEntity.status(403).body(movieUtils.toJson("Forbidden"));
        }
        Movie foundMovie = movieRepository.findById(id);
        if (foundMovie == null) {
            return ResponseEntity.status(404).body(movieUtils.toJson("Movie not found"));
        }

        for (MovieSession session : foundMovie.getMovieSessions()) {
            movieSessionRepository.delete(session);
        }

        movieRepository.delete(foundMovie);
        return ResponseEntity.status(200).body(movieUtils.toJson("Movie deleted"));
    }
}
