package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.model.Rating;
import com.example.demo.model.Users;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Transactional
    public String rateMovie(String movieId, int value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = movieRepository.findByImdbId(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Optional<Rating> existing = ratingRepository.findByUserAndMovie(user, movie);

        Rating rating = existing.orElse(new Rating());
        rating.setUser(user);
        rating.setMovie(movie);
        rating.setRating(value);

        ratingRepository.save(rating);

        return existing.isPresent() ? "Rating updated" : "Rating added";
    }
}
