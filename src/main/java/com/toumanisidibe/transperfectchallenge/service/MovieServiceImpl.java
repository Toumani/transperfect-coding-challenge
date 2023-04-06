package com.toumanisidibe.transperfectchallenge.service;

import com.toumanisidibe.transperfectchallenge.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final RestTemplate restTemplate;

    @Autowired
    public MovieServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Movie> getAllMovies() {
        try {
            ResponseEntity<List<Movie>> response = restTemplate.exchange(
                    "/movies", // TODO replace with actual URL
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return response.getBody();
        } catch (IllegalArgumentException ex) {
            return Arrays.asList(
                    new Movie(1L, "The Shawshank Redemption", "Drama", 1994, "Frank Darabont"),
                    new Movie(2L, "The Godfather", "Drama", 1972, "Francis Ford Coppola"),
                    new Movie(3L, "The Dark Knight", "Action", 2008, "Christopher Nolan"),
                    new Movie(6L, "The Matrix", "Action", 1999, "The Wachowskis")
            );
        }
    }
}
