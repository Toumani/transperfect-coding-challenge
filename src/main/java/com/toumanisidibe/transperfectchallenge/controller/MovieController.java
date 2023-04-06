package com.toumanisidibe.transperfectchallenge.controller;

import com.toumanisidibe.transperfectchallenge.model.Movie;
import com.toumanisidibe.transperfectchallenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/recommendations")
    public List<Movie> getRecommendations(@RequestParam("genre") String genre) {
        return movieService.getAllMovies().stream()
                .filter(it -> it.getGenre().equals(genre))
                .sorted(Comparator.comparingInt(Movie::getReleaseYear).reversed())
                .collect(Collectors.toList());
    }
}
