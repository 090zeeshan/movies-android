package com.vd.movies.repository.api

import com.vd.movies.repository.model.Movie
import com.vd.movies.repository.model.SearchResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


class Api : IApi {
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

    override suspend fun searchMovies(key: String): List<Movie> {
        val movies = moviesService.search(key).movies
        return movies ?: emptyList()
    }

    override suspend fun getMovieByImdbId(imdbId: String): Movie {
        return moviesService.getByImdbId(imdbId)
    }
}