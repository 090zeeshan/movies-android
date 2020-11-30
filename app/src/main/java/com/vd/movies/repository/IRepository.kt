package com.vd.movies.repository

import com.vd.movies.repository.model.Movie

interface IRepository {
    suspend fun searchMovies(key: String): List<Movie>
}