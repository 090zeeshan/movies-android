package com.vd.movies.data.repository

import androidx.lifecycle.LiveData
import com.vd.movies.data.db.entity.Movie

interface Repository {
    suspend fun searchMovies(key: String): List<Movie>
    suspend fun fetchMovieByImdbId(imdbId: String): Movie?
    suspend fun updateMovie(movie: Movie)
    fun fetchWatchedListMovies(limit: Int = -1): LiveData<List<Movie>>
    fun fetchWatchlistMovies(limit: Int = -1): LiveData<List<Movie>>
    fun fetchFavoriteMovies(limit: Int = -1): LiveData<List<Movie>>
}
