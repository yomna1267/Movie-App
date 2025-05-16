package com.example.demo.controller;

import com.example.demo.dto.MovieDTO;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    MovieRepository movieRepository;
    @GetMapping
    public List<Movie> getAllMovies() {
        System.out.println("getAllMovies");
        List<Movie> movies = movieRepository.findAll();
        return movies;
    }

    @GetMapping("/{imdbID}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String imdbID) {
        Optional<Movie> movie = movieRepository.findByImdbId(imdbID);
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        }
        return ResponseEntity.notFound().build();
    }
}
