package com.example.demo.repository;

import com.example.demo.model.Movie;
import com.example.demo.model.Rating;
import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserAndMovie(Users user, Movie movie);
}
