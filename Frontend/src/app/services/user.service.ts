import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../models/MovieResponse';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient) {}

  rateMovie(imdbId: string, ratingValue: number) {
    const token = localStorage.getItem('token');
    const params = new HttpParams()
      .set('imdbID', imdbId)
      .set('ratingValue', ratingValue.toString());

    return this.http.post<{ message: string }>(
      `${this.apiUrl}/rate`,
      null,
      {
        params,
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );
  }


  searchMovies(term: string) {
    const token = localStorage.getItem('token');

    return this.http.get<Movie[]>(`${this.apiUrl}/search`, {
      params: { term },
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }
}
