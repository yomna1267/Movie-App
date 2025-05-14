package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.service.AdminUserService;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    MovieService movieService;

    @Autowired
    AdminUserService adminUserService;

    @GetMapping("/search")
    public ResponseEntity<?> searchMovies(@RequestParam String term) {
        System.out.println(term);
        return ResponseEntity.ok(movieService.searchMoviesFromOmdb(term));
    }

    @PostMapping("/")
    public ResponseEntity<?> addMovie(@RequestParam String imdbID) {
        return ResponseEntity.ok(adminUserService.addMovie(imdbID));
    }

    @PostMapping("/batch")
    public ResponseEntity<?> addMovieBatch(@RequestParam List<String> imdbIDs) {
        return ResponseEntity.ok(adminUserService.addMovies(imdbIDs));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteMovie(@RequestParam Long id) {
        return ResponseEntity.ok(adminUserService.deleteMovie(id));
    }

    @DeleteMapping("/batch")
    public ResponseEntity<?> deleteMovieBatch(@RequestParam List<Long> IDs) {
        return ResponseEntity.ok(adminUserService.deleteMovies(IDs));
    }


}
