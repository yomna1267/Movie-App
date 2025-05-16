package com.example.demo.service;

import com.example.demo.dto.OmdbResponseByID;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.model.Movie;
import com.example.demo.model.Users;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUserService {

    @Autowired
    MovieService movieService;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieMapper movieMapper;

    @Transactional
    public String addMovie(String imdbID) {
        Optional<Movie> found = movieRepository.findByImdbId(imdbID);
        if (found.isPresent()) {
            return "Movie with title " + found.get().getTitle() + " already exists.";
        }
        OmdbResponseByID omdbResponseByID = movieService.getMovieByID(imdbID);
        System.out.println("OMDB Type: " + omdbResponseByID.getType());
        Movie movie = movieMapper.mapToEntity(omdbResponseByID);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users currentUser =  userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        movie.setAddedBy(currentUser);

        movieRepository.save(movie);

        return "Movie added successfully!";
    }

    @Transactional
    public String addMovies(List<String> imdbIDs) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        for (String imdbID : imdbIDs) {
            Optional<Movie> found = movieRepository.findByImdbId(imdbID);
            if (found.isPresent()) {
                return "Movie with title " + found.get().getTitle() + " already exists.";
            }
            OmdbResponseByID omdbResponse = movieService.getMovieByID(imdbID);
            Movie movie = movieMapper.mapToEntity(omdbResponse);
            movie.setAddedBy(currentUser);
            movieRepository.save(movie);
        }

        return "Movies added successfully!";
    }

    @Transactional
    public String deleteMovie(String imdbID) {
        Movie movie = movieRepository.findByImdbId(imdbID)
                .orElseThrow(() -> new RuntimeException("Movie with ID " + imdbID + " not found"));
        movieRepository.delete(movie);
        return "Movie removed successfully";
    }

    @Transactional
    public String deleteMovies(List<String> imdbIDs) {
        for(String id : imdbIDs) {
            Movie movie = movieRepository.findByImdbId(id).orElseThrow(() -> new RuntimeException("Movie with ID " + id + " not found"));
            movieRepository.delete(movie);
        }
        return "Movie removed successfully";
    }
}
