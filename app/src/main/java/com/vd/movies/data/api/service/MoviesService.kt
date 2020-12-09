package com.vd.movies.data.api.service

import com.vd.movies.data.api.model.AMovie
import com.vd.movies.data.api.model.ASearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET(".")
    suspend fun search(@Query("s") key:String): ASearchResult

    @GET(".")
    suspend fun getByImdbId(@Query("i") imdbId:String): AMovie
}