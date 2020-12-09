package com.vd.movies.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vd.movies.data.db.entity.Movie

interface IRepository {
    suspend fun searchMovies(key: String): List<Movie>
    suspend fun getMovieByImdbId(imdbId: String): Movie?
    suspend fun updateMovie(movie: Movie)
    fun fetchWatchedListMovies(limit: Int = -1): LiveData<List<Movie>>
    fun fetchWatchlistMovies(limit: Int = -1): LiveData<List<Movie>>
    fun fetchFavoriteMovies(limit: Int = -1): LiveData<List<Movie>>
}