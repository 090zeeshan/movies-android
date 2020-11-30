package com.vd.movies.repository.api

import com.vd.movies.repository.model.Movie
import com.vd.movies.repository.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET(".")
    suspend fun search(@Query("s") key:String): SearchResult
}