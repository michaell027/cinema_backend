package com.example.cinema_backend.controllers;

import com.example.cinema_backend.models.Movie;
import com.example.cinema_backend.models.MovieSession;
import com.example.cinema_backend.models.MovieWithSessions;
import com.example.cinema_backend.models.SessionTime;
import com.example.cinema_backend.repositories.MovieSessionRepository;
import com.example.cinema_backend.repositories.UserRepository;
import com.example.cinema_backend.services.TokenService;
import com.example.cinema_backend.utils.MovieUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/movies-sessions")
public class MovieSessionController {

    @Autowired
    private MovieSessionRepository movieSessionRepository;

    private MovieUtils movieUtils = new MovieUtils();

    private TokenService tokenService = TokenService.getInstance();

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/today")
    public ResponseEntity<String> getTodayMovies(@RequestHeader("Authorization") String token) {
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }
        List <MovieWithSessions> moviesWithSessions = getMoviesSessionsByDate(LocalDate.now().toString());

        if (moviesWithSessions.isEmpty()) {
            Gson gson = new Gson();
            return ResponseEntity.status(404).body(gson.toJson("No movies found"));
        }

        return ResponseEntity.status(200).body(movieUtils.toJson(moviesWithSessions));
    }

    @GetMapping("/{date}")
    public ResponseEntity<String> getMoviesByDate(@PathVariable String date, @RequestHeader("Authorization") String token) {
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }

        List <MovieWithSessions> moviesWithSessions;

        moviesWithSessions = getMoviesSessionsByDate(date);

        if (moviesWithSessions.isEmpty()) {
            Gson gson = new Gson();
            return ResponseEntity.status(404).body(gson.toJson("No movies found for " + date));
        }

        return ResponseEntity.status(200).body(movieUtils.toJson(moviesWithSessions));
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<String> addMovieSession(@PathVariable long id, @RequestBody MovieSession movieSession, @RequestHeader("Authorization") String token) {
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }
        if (!tokenService.isAdmin(token, userRepository)) {
            return ResponseEntity.status(403).body(movieUtils.toJson("Forbidden"));
        }
        Movie movie = new Movie();
        movie.setId(id);
        movieSession.setMovie(movie);
        movieSessionRepository.save(movieSession);
        return ResponseEntity.status(200).body(movieUtils.toJson("Movie session added"));
    }


    public List<MovieWithSessions> getMoviesSessionsByDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);

        List <MovieWithSessions> moviesWithSessions = new ArrayList<>();

        List<MovieSession> sessions = movieSessionRepository.findByStartTimeBetween(startOfDay, endOfDay);

        for (MovieSession session : sessions) {
            Movie movie = session.getMovie();
            SessionTime sessionTime = new SessionTime(session.getStartTime(), session.getEndTime(), session.getPrice());

            if (moviesWithSessions.isEmpty()) {
                List<SessionTime> sessionTimes = new ArrayList<>();
                sessionTimes.add(sessionTime);
                MovieWithSessions movieWithSessions = new MovieWithSessions(movie, sessionTimes);
                moviesWithSessions.add(movieWithSessions);
            } else {
                boolean movieExists = false;
                for (MovieWithSessions movieWithSessions : moviesWithSessions) {
                    if (movieWithSessions.getMovie().getId() == movie.getId()) {
                        movieExists = true;
                        movieWithSessions.getSessions().add(sessionTime);
                    }
                }
                if (!movieExists) {
                    List<SessionTime> sessionTimes = new ArrayList<>();
                    sessionTimes.add(sessionTime);
                    MovieWithSessions movieWithSessions = new MovieWithSessions(movie, sessionTimes);
                    moviesWithSessions.add(movieWithSessions);
                }
            }
        }

        return moviesWithSessions;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovieSession(@PathVariable long id, @RequestBody MovieSession movieSession, @RequestHeader("Authorization") String token) {
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(movieUtils.toJson("Unauthorized"));
        }
        if (!tokenService.isAdmin(token, userRepository)) {
            return ResponseEntity.status(403).body(movieUtils.toJson("Forbidden"));
        }
        Movie movie = new Movie();
        movie.setId(id);
        movieSession.setMovie(movie);
        movieSessionRepository.delete(movieSession);
        return ResponseEntity.status(200).body(movieUtils.toJson("Movie session deleted"));
    }
}
