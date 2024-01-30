package com.example.cinema_backend.models;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieWithSessions {
    @Expose
    private Movie movie;
    
    @Expose
    private List<SessionTime> sessions;
}
