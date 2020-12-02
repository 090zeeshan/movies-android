package com.vd.movies.repository

import com.vd.movies.repository.api.Api
import com.vd.movies.repository.api.IApi
import com.vd.movies.repository.model.Movie

class Repository: IRepository {
    private val api: IApi = Api()
    override suspend fun searchMovies(key: String): List<Movie> {
        return api.searchMovies(key)
    }

    override suspend fun getMovieByImdbId(imdbId: String): Movie {
        return api.getMovieByImdbId(imdbId)
    }
}