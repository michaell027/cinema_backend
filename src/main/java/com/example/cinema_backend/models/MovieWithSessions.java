package com.example.cinema_backend.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieWithSessions {
    private Movie movie;
    private List<SessionTime> sessions;
}
