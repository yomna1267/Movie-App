package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OmdbResponseByID {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Released")
    private String released;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("Rated")
    private String rated;

}
