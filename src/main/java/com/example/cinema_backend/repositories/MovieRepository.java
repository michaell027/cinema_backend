package com.example.cinema_backend.repositories;

import com.example.cinema_backend.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(exported = false)
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    public Movie findByTitle(String title);
    public Movie findById(long id);
}