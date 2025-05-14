package com.example.demo.controller;

import com.example.demo.dto.MovieDTO;
import com.example.demo.mapper.MovieMapper;
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
public class RegularUserController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    RatingService ratingService;

    @GetMapping("/search")
    public ResponseEntity<List<MovieDTO>> search(@RequestParam String term) {
        List<MovieDTO> results = movieRepository.findByTitleContainingIgnoreCase(term)
                .stream()
                .map(movieMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(results);
    }
    @PostMapping("/rate")
    public ResponseEntity<?> rateMovie(@RequestParam Long movieId, @RequestParam int ratingValue) {
        String message = ratingService.rateMovie(movieId, ratingValue);
        return ResponseEntity.ok(Map.of("message", message));
    }

}
