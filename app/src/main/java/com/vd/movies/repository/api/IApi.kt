package com.vd.movies.repository.api

import com.vd.movies.repository.model.Movie
import com.vd.movies.repository.model.SearchResult

interface IApi {
    suspend fun searchMovies(key: String): List<Movie>
}