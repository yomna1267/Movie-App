package com.example.demo.mapper;

import com.example.demo.dto.MovieDTO;
import com.example.demo.dto.OmdbResponseByID;
import com.example.demo.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie mapToEntity(OmdbResponseByID omdbResponseByID) {
        Movie movie = new Movie();
        movie.setTitle(omdbResponseByID.getTitle());
        movie.setYear(omdbResponseByID.getYear());
        movie.setType(omdbResponseByID.getType());
        movie.setGenre(omdbResponseByID.getGenre());
        movie.setDirector(omdbResponseByID.getDirector());
        movie.setActors(omdbResponseByID.getActors());
        movie.setRuntime(omdbResponseByID.getRuntime());
        movie.setLanguage(omdbResponseByID.getLanguage());
        movie.setReleaseDate(omdbResponseByID.getReleased());
        movie.setImdbId(omdbResponseByID.getImdbID());
        movie.setRated(omdbResponseByID.getRated());
        return movie;
    }

    public MovieDTO mapToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setYear(movie.getYear());
        movieDTO.setType(movie.getType());
        movieDTO.setImdbID(movie.getImdbId());
        return movieDTO;
    }
}
