import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/MovieResponse';
import { UserService } from '../../services/user.service';
import { OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-dashboard',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './user-dashboard.component.html',
  styleUrl: './user-dashboard.component.css'
})
export class UserDashboardComponent implements OnInit{
  movies: Movie[] = [];
  searchTerm: string = '';
  message: string = '';

  constructor(private movieService: MovieService, private userService: UserService) {}

  ngOnInit(): void {
    this.getAllMovies();
  }

  getAllMovies(): void {
    this.movieService.getAllMovies().subscribe({
      next: (data) => {
        this.movies = data.map(movie => ({ ...movie, showDetails: false }));
      },
      error: (err) => console.error('Error fetching movies:', err)
    });
  }

  searchMovies(): void {
    if (this.searchTerm.trim()) {
      this.userService.searchMovies(this.searchTerm).subscribe({
        next: (data) => {
          this.movies = data.map(movie => ({ ...movie, showDetails: false }));
        },
        error: (err) => console.error('Error searching movies:', err)
      });
    } else {
      this.getAllMovies();
    }
  }

  toggleDetails(movie: Movie): void {
    movie.showDetails = !movie.showDetails;
  }

  rateMovie(movie: Movie): void {
    if (!movie.userRating || movie.userRating < 1 || movie.userRating > 5) {
      this.message = 'Rating must be between 1 and 5.';
      alert(this.message);
      return;
    }

    this.userService.rateMovie(movie.imdbId, movie.userRating).subscribe({
      next: (res) => {
        this.message = res.message || 'Rated successfully!';
        alert(this.message);
      },
      error: (err) => {
        console.error('Rating error:', err);
        this.message = 'Failed to rate movie.';
        alert(this.message);
      }
    });
  }

}
