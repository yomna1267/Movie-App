import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../models/MovieResponse';


@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private movieUrl = 'http://localhost:8080/movies';

  constructor(private http : HttpClient) { }
    getAllMovies(): Observable<Movie[]> {
      const token = localStorage.getItem('token');
      console.log('Token:', token);
      return this.http.get<Movie[]>(this.movieUrl, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    getMovieByImdbId(imdbID: string): Observable<Movie> {
      const token = localStorage.getItem('token');
      console.log('Token:', token);
      return this.http.get<Movie>(`${this.movieUrl}/${imdbID}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
    }
}
