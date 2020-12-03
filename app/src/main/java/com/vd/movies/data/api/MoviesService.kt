package com.vd.movies.data.api

import com.vd.movies.data.model.MovieDetail
import com.vd.movies.data.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET(".")
    suspend fun search(@Query("s") key:String): SearchResult

    @GET(".")
    suspend fun getByImdbId(@Query("i") imdbId:String): MovieDetail
}