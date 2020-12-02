package com.vd.movies.repository

import com.vd.movies.repository.model.Movie

interface IRepository {
    suspend fun searchMovies(key: String): List<Movie>
    suspend fun getMovieByImdbId(imdbId: String): Movie
    suspend fun updateMovie(movie: Movie)
    suspend fun fetchWatchedListMovies(): List<Movie>
    suspend fun fetchWatchlistMovies(): List<Movie>
    suspend fun fetchFavoriteMovies(): List<Movie>
}