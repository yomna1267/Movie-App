import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Movie } from '../../models/MovieResponse';
import { AdminService } from '../../services/admin.service';
import { MovieService } from '../../services/movie.service';
import { MovieDTO } from '../../models/MovieDTO';

@Component({
  selector: 'app-admin-dashboard',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
  standalone: true,
})
export class AdminDashboardComponent implements OnInit {
  searchTerm = '';
  searchResults: MovieDTO[] = [];
  selectedMovies: string[] = [];
  appMovies: Movie[] = [];

  constructor(private adminService: AdminService, private movieService: MovieService) {}

  ngOnInit(): void {
    this.loadAppMovies();
  }

  loadAppMovies(): void {
    this.movieService.getAllMovies().subscribe({
      next: (movies) => {
        this.appMovies = movies.map(m => ({ ...m, selected: false }));
      },
      error: (err) => console.error('Failed to load app movies:', err),
    });
  }

  onSearch(): void {
    if (!this.searchTerm.trim()) return;

    this.adminService.searchMovies(this.searchTerm).subscribe({
      next: (res: MovieDTO[]) => {
        console.log(res);
        this.searchResults = res.map(m => ({ ...m, selected: false }));
        this.selectedMovies = [];
      },
      error: (err) => console.error('Search failed:', err),
    });
  }

  updateSelectedMoviesFromSearch(): void {
    this.selectedMovies = this.searchResults
      .filter(m => m.selected)
      .map(m => m.imdbID);
  }

  updateSelectedMoviesFromApp(): void {
    this.selectedMovies = this.appMovies
      .filter(m => m.selected)
      .map(m => m.imdbId);
  }

  toggleSearchMovieSelection(movie: MovieDTO): void {
    this.updateSelectedMoviesFromSearch();
  }

  toggleAppMovieSelection(movie: Movie): void {
    this.updateSelectedMoviesFromApp();
  }
  addMovie(imdbID: string): void {
    this.adminService.addMovie(imdbID).subscribe({
      next: (response: { message: string }) => {
        alert(response.message);
        this.onSearch()
        this.loadAppMovies();
      },
      error: (err) => console.error('Add movie failed:', err),
    });
  }


  addMoviesBatch(): void {
    if (this.selectedMovies.length < 2) return;

    this.adminService.addMovieBatch(this.selectedMovies).subscribe({
      next: (response: { message: string }) => {
        alert(response.message);
        this.searchResults.forEach(m => (m.selected = false));
        this.selectedMovies = [];
        this.loadAppMovies();
      },
      error: (err) => console.error('Batch add failed:', err),
    });
  }

  deleteMovie(id?: string): void {
    if (!id) {
      console.error('No ID to delete');
      return;
    }
    this.adminService.deleteMovie(id).subscribe({
      next: () => {
        alert('Movie deleted successfully!');
        this.loadAppMovies();
      },
      error: (err) => console.error('Delete failed:', err),
    });
  }

  deleteMoviesBatch(): void {
    const imdbIDsToDelete = this.appMovies
      .filter(m => m.selected && m.imdbId)
      .map(m => m.imdbId);

    if (imdbIDsToDelete.length < 2) return;

    this.adminService.deleteMovieBatch(imdbIDsToDelete).subscribe({
      next: () => {
        alert('Batch movies deleted successfully!');
        this.appMovies.forEach(m => (m.selected = false));
        this.selectedMovies = [];
        this.loadAppMovies();
      },
      error: (err) => console.error('Batch delete failed:', err),
    });
  }

  get canDeleteBatch(): boolean {
    return this.appMovies.filter(m => m.selected && m.imdbId).length >= 2;
  }

  get canAddBatch(): boolean {
    return this.searchResults.filter(m => m.selected).length >= 2;
  }

}
