package com.example.cinema_backend.controllers;

import com.example.cinema_backend.models.Movie;
import com.example.cinema_backend.models.MovieSession;
import com.example.cinema_backend.models.MovieWithSessions;
import com.example.cinema_backend.models.SessionTime;
import com.example.cinema_backend.repositories.MovieSessionRepository;
import com.example.cinema_backend.utils.MovieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieSessionController {

    @Autowired
    private MovieSessionRepository movieSessionRepository;

    private MovieUtils movieUtils = new MovieUtils();

    @GetMapping("/movies/today")
    public ResponseEntity<String> getTodayMovies() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        List<MovieSession> sessions = movieSessionRepository.findByStartTimeBetween(startOfDay, endOfDay);

        if (sessions.isEmpty()) {
            return ResponseEntity.status(404).body(movieUtils.toJson("No movies found"));
        }

        List <MovieWithSessions> moviesWithSessions = new ArrayList<>();

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

            return ResponseEntity.status(200).body(movieUtils.toJson(moviesWithSessions));
        }

}
