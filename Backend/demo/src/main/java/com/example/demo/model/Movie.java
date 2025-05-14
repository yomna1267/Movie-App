package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String imdbId;
    String title;
    String year;
    String genre;
    String director;
    String runtime;
    String actors;
    String language;
    String releaseDate;
    @ManyToOne
    @JoinColumn(name = "added_by")
    private Users addedBy;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Rating> ratings;
}
