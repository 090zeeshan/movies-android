package com.vd.movies.data.api

import com.vd.movies.data.api.model.AMovie
import com.vd.movies.data.api.service.MoviesService
import com.vd.movies.data.api.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Api {
    suspend fun searchMovies(key: String): List<AMovie>
    suspend fun getMovieByImdbId(imdbId: String): AMovie

    class Builder(){
        fun build(): Api{
            return ApiImp()
        }
    }
}

private class ApiImp : Api {
    private val moviesService: MoviesService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor {
                val requestBuilder = it.request().newBuilder()
                val originalUrl = it.request().url
                val newUrl =
                    originalUrl.newBuilder().addQueryParameter("apikey", "5111028f").build()
                requestBuilder.url(newUrl)
                it.proceed(requestBuilder.build())
            }
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        moviesService = retrofit.create(MoviesService::class.java)
    }

    override suspend fun searchMovies(key: String): List<AMovie> {
        val movies = moviesService.search(key).movies
        return movies ?: emptyList()
    }

    override suspend fun getMovieByImdbId(imdbId: String): AMovie {
        return moviesService.getByImdbId(imdbId)
    }
}