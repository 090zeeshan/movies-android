package com.vd.movies.data.repository

import com.vd.movies.data.api.IApi
import com.vd.movies.data.db.AppDatabase
import com.vd.movies.data.model.Movie
import com.vd.movies.data.model.MovieDetail
import com.zain.android.internetconnectivitylibrary.ConnectionUtil

class Repository(
    val api: IApi,
    val db: AppDatabase,
    val connectionUtil: ConnectionUtil
) : IRepository {

    override suspend fun searchMovies(key: String): List<Movie> {
        if (!connectionUtil.isOnline) {
            return db.movieDao().searchByTitle(key)
        }

        val movies = api.searchMovies(key)
        db.movieDao().insertAll(movies)
        return movies
    }

    override suspend fun getMovieByImdbId(imdbId: String): MovieDetail? {
        var result = db.movieDetailDao().getByImdbId(imdbId)
        if(!connectionUtil.isOnline){
            return result
        }

        if (result == null) {
            result = api.getMovieByImdbId(imdbId)
            db.movieDetailDao().upsert(result)
        }
        return result
    }

    override suspend fun updateMovie(movie: MovieDetail) {
        db.movieDetailDao().upsert(movie)
    }

    override suspend fun fetchWatchedListMovies(): List<Movie> {
        return mapMovieDetailsToMovies(db.movieDetailDao().getWatchedList())
    }

    override suspend fun fetchWatchlistMovies(): List<Movie> {
        return mapMovieDetailsToMovies(db.movieDetailDao().getWatchlist())
    }

    override suspend fun fetchFavoriteMovies(): List<Movie> {
        return mapMovieDetailsToMovies(db.movieDetailDao().getFavorites())
    }

    private fun mapMovieDetailsToMovies(movieDetails: List<MovieDetail>): List<Movie> {
        return movieDetails.map { Movie(it.imdbId, it.title, it.year, it.poster, it.type) }
    }
}