package com.example.cinema_backend.repositories;

import com.example.cinema_backend.models.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(exported = false)
@Repository
public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {
    List<MovieSession> findByStartTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
