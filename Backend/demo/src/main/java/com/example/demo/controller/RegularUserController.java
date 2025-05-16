package com.example.demo.controller;

import com.example.demo.dto.MovieDTO;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class RegularUserController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    RatingService ratingService;

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> search(@RequestParam String term) {
        List<Movie> results = movieRepository.findByTitleContainingIgnoreCase(term);
        return ResponseEntity.ok(results);
    }
    @PostMapping("/rate")
    public ResponseEntity<?> rateMovie(@RequestParam String imdbID, @RequestParam int ratingValue) {
        String message = ratingService.rateMovie(imdbID, ratingValue);
        return ResponseEntity.ok(Map.of("message", message));
    }

}
