package com.example.cinema_backend.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionTime {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal price;
}
