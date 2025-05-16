package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.service.AdminUserService;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
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
        String message = adminUserService.addMovie(imdbID);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch")
    public ResponseEntity<?> addMovieBatch(@RequestParam List<String> imdbIDs) {
        String message = adminUserService.addMovies(imdbIDs);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteMovie(@RequestParam String imdbID) {
        String message = adminUserService.deleteMovie(imdbID);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/batch")
    public ResponseEntity<?> deleteMovieBatch(@RequestBody List<String> imdbIDs) {
        String message = adminUserService.deleteMovies(imdbIDs);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }



}
