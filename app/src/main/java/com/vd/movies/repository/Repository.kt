package com.vd.movies.repository

import android.content.Context
import com.vd.movies.repository.api.Api
import com.vd.movies.repository.api.IApi
import com.vd.movies.repository.db.AppDatabase
import com.vd.movies.repository.model.Movie

class Repository(context: Context): IRepository {
    private val api: IApi = Api()
    private val db = AppDatabase.getInstance(context)

    override suspend fun searchMovies(key: String): List<Movie> {
        return api.searchMovies(key)
    }

    override suspend fun getMovieByImdbId(imdbId: String): Movie {
        var result = db.movieDao().getByImdbId(imdbId)
        if(result == null){
            result = api.getMovieByImdbId(imdbId)
            db.movieDao().upsert(result)
        }
        return result
    }

    override suspend fun updateMovie(movie: Movie) {
            db.movieDao().upsert(movie)
    }

    override suspend fun fetchWatchedListMovies(): List<Movie> {
        return db.movieDao().getWatchedList()
    }

    override suspend fun fetchWatchlistMovies(): List<Movie> {
        return db.movieDao().getWatchlist()
    }

    override suspend fun fetchFavoriteMovies(): List<Movie> {
        return db.movieDao().getFavorites()
    }
}