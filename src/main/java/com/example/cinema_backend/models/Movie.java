package com.example.cinema_backend.models;

import com.google.gson.Gson;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private String duration;

    @Column(name = "rating")
    private int rating;

    @Column(name = "genre")
    private String genre;

    @Column(name = "release_date")
    private String releaseDate;

    public Movie(String title, String description, String duration, int rating, String genre, String releaseDate) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.rating = rating;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title=" + title +
                ", description=" + description +
                ", duration=" + duration +
                ", rating=" + rating +
                ", genre=" + genre +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
