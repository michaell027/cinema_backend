package com.example.cinema_backend.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.google.gson.annotations.Expose;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionTime {
    @Expose
    private LocalDateTime startTime;

    @Expose
    private LocalDateTime endTime;

    @Expose
    private BigDecimal price;
}
