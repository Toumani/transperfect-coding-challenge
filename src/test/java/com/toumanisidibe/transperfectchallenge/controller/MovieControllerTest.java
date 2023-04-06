package com.toumanisidibe.transperfectchallenge.controller;

import com.toumanisidibe.transperfectchallenge.model.Movie;
import com.toumanisidibe.transperfectchallenge.service.MovieService;
import org.json.JSONArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private MovieService movieService;

    @Test
    @DisplayName("/recommendations endpoint integration test")
    public void getRecommendations() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                new URL("http://localhost:" + port + "/recommendations?genre={genre}").toString(),
                HttpMethod.GET,
                entity,
                String.class,
                "Action"
        );
        assertEquals("/recommendations endpoint did not respond with HTTP status code 200", HttpStatusCode.valueOf(200), response.getStatusCode());
        assertDoesNotThrow(() -> new JSONArray(response.getBody()), "/recommendations endpoint did not return a valid JSON.");
    }

    @Test
    @DisplayName("Recommendations unit test")
    public void getRecommendationsUnitTest() throws Exception {
        String genre = "Action";

        // given
        when(movieService.getAllMovies()).then((Answer<List<Movie>>) invocation -> Arrays.asList(
                new Movie(1L, "The Shawshank Redemption", "Drama", 1994, "Frank Darabont"),
                new Movie(2L, "The Godfather", "Drama", 1972, "Francis Ford Coppola"),
                new Movie(3L, "The Dark Knight", "Action", 2008, "Christopher Nolan"),
                new Movie(6L, "The Matrix", "Action", 1999, "The Wachowskis")
        ));

        // when
        MovieController movieController = new MovieController(movieService);
        List<Movie> recommendedMovies = movieController.getRecommendations(genre);

        // then
        recommendedMovies.forEach(movie -> assertEquals("", genre, movie.getGenre()));
        for (int i = 0; i < recommendedMovies.size() - 1; i++)
            assertTrue(recommendedMovies.get(i).getReleaseYear() >= recommendedMovies.get(i + 1).getReleaseYear());
    }
}
