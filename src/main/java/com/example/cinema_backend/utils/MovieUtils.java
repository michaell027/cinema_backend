package com.example.cinema_backend.utils;

import com.example.cinema_backend.serializers.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class MovieUtils {
    public static String toJson(Object object) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
        return gson.toJson(object);
    }
}
