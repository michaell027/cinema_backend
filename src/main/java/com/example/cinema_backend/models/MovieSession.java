package com.example.cinema_backend.models;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "movie_session")
@ToString
public class MovieSession {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @Expose
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Expose
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Expose
    @Column(name = "price")
    private BigDecimal price;

    public MovieSession(Movie movie, LocalDateTime startTime, LocalDateTime endTime, BigDecimal price) {
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }
}
