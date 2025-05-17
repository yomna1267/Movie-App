package com.example.demo.service;

import com.example.demo.dto.MovieDTO;
import com.example.demo.dto.OmdbResponseByID;
import com.example.demo.dto.OmdbSearchResponse;
import com.example.demo.exceptions.MovieNotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Value("${omdb.api.key}")
    private String omdbKey;

    private RestTemplate restTemplate = new RestTemplate();

    public List<MovieDTO> searchMoviesFromOmdb(String term) {
        String url = "http://www.omdbapi.com/?apikey=" + omdbKey + "&s=" + term;
        OmdbSearchResponse response = restTemplate.getForObject(url, OmdbSearchResponse.class);

        System.out.println("OMDb API Response mapped: " + response);

        if (response != null && "True".equalsIgnoreCase(response.getResponse())) {
            return response.getSearch();
        }

        throw new MovieNotFoundException("No movies found.");
    }


    public OmdbResponseByID getMovieByID(String imdbID) {
        String url = "http://www.omdbapi.com/?apikey=" + omdbKey + "&i=" + imdbID;
        OmdbResponseByID response = restTemplate.getForObject(url, OmdbResponseByID.class);
        System.out.println("OMDb API Response mapped: " + response);
        if (response != null) {
            return response;
        }
        throw new MovieNotFoundException("No movies found.");
    }


}
