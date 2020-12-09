package com.vd.movies.data.api

import com.vd.movies.data.api.model.AMovie

interface IApi {
    suspend fun searchMovies(key: String): List<AMovie>
    suspend fun getMovieByImdbId(imdbId: String): AMovie
}