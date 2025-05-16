import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { Movie } from '../models/MovieResponse';
import { MovieDTO } from '../models/MovieDTO';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private adminUrl = 'http://localhost:8080/admin';

  constructor(private http : HttpClient) { }

  searchMovies(term: string): Observable<MovieDTO[]> {
    const token = localStorage.getItem('token');
    console.log('Token:', token);
    return this.http.get<MovieDTO[]>(`${this.adminUrl}/search`, {
      params: new HttpParams().set('term', term),
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  addMovie(imdbID: string): Observable<any> {
    const params = new HttpParams().set('imdbID', imdbID);
    const token = localStorage.getItem('token');
    console.log('Token:', token);
    return this.http.post(`${this.adminUrl}/`, null, { params, headers: {
      Authorization: `Bearer ${token}`
    } });
  }

  addMovieBatch(imdbIDs: string[]): Observable<any> {
    let params = new HttpParams();
    const token = localStorage.getItem('token');
    console.log('Token:', token);
    imdbIDs.forEach(id => {
      params = params.append('imdbIDs', id);
    });
    return this.http.post(`${this.adminUrl}/batch`, null, { params, headers: {
      Authorization: `Bearer ${token}`
    } });
  }

  deleteMovie(imdbID: string): Observable<any> {
    const token = localStorage.getItem('token');
    console.log('Token:', token);
    const params = new HttpParams().set('imdbID', imdbID);
    return this.http.delete(`${this.adminUrl}/`, { params, headers: {
      Authorization: `Bearer ${token}`
    } });
  }

  deleteMovieBatch(imdbIDs: string[]): Observable<any> {
    const token = localStorage.getItem('token');
    return this.http.request('delete', `${this.adminUrl}/batch`, {
      body: imdbIDs,
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }



}
