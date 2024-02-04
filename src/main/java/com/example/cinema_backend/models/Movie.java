package com.example.cinema_backend.models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
@ToString (exclude = "movieSessions")
public class Movie {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Expose
    @Column(name = "title")
    private String title;

    @Expose
    @Column(name = "description")
    private String description;

    @Expose
    @Column(name = "duration")
    private String duration;

    @Expose
    @Column(name = "rating")
    private int rating;

    @Expose
    @Column(name = "genre")
    private String genre;

    @Expose
    @Column(name = "release_date")
    private String releaseDate;

    @Expose
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MovieSession> movieSessions;


    public Movie(String title, String description, String duration, String genre, String releaseDate) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.rating = 0;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
