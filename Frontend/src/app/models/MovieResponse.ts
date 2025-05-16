export interface Movie {
  id?: number;
  imdbId: string;
  title: string;
  year: string;
  type: string;
  genre: string;
  director: string;
  runtime: string;
  actors: string;
  language: string;
  releaseDate: string;
  rated?: string;

  userRating?: number;
  showDetails?: boolean;
  selected?: boolean;
}
