package com.vd.movies.data.repository

import com.vd.movies.data.model.Movie
import com.vd.movies.data.model.MovieDetail

interface IRepository {
    suspend fun searchMovies(key: String): List<Movie>
    suspend fun getMovieByImdbId(imdbId: String): MovieDetail?
    suspend fun updateMovie(movie: MovieDetail)
    suspend fun fetchWatchedListMovies(): List<Movie>
    suspend fun fetchWatchlistMovies(): List<Movie>
    suspend fun fetchFavoriteMovies(): List<Movie>
}