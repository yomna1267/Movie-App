package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(unique = true, nullable = false)
    String imdbId;
    String title;
    String year;
    String genre;
    String type;
    String director;
    String runtime;
    String actors;
    String language;
    String releaseDate;
    String rated;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "movie-rating")
    private List<Rating> ratings;

    @ManyToOne
    @JoinColumn(name = "added_by")
    @JsonBackReference(value = "user-movie")
    private Users addedBy;
}
