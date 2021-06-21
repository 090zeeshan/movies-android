package com.vd.movies.data.api

import com.vd.movies.data.api.model.AMovie

interface Api {
    suspend fun searchMovies(key: String): List<AMovie>
    suspend fun fetchMovieByImdbId(imdbId: String): AMovie

}
