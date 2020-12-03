package com.vd.movies.data.api

import com.vd.movies.data.model.Movie
import com.vd.movies.data.model.MovieDetail

interface IApi {
    suspend fun searchMovies(key: String): List<Movie>
    suspend fun getMovieByImdbId(imdbId: String): MovieDetail
}