package com.vd.movies.data.repository

import androidx.lifecycle.LiveData
import com.vd.movies.data.api.Api
import com.vd.movies.data.api.model.AMovie
import com.vd.movies.data.db.AppDatabase
import com.vd.movies.data.db.entity.Movie
import com.zain.android.internetconnectivitylibrary.ConnectionUtil
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

interface Repository {
    suspend fun searchMovies(key: String): List<Movie>
    suspend fun getMovieByImdbId(imdbId: String): Movie?
    suspend fun updateMovie(movie: Movie)
    fun fetchWatchedListMovies(limit: Int = -1): LiveData<List<Movie>>
    fun fetchWatchlistMovies(limit: Int = -1): LiveData<List<Movie>>
    fun fetchFavoriteMovies(limit: Int = -1): LiveData<List<Movie>>

    class Builder(val api: Api, val db: AppDatabase, val connectionUtil: ConnectionUtil) {
        fun build(): Repository {
            return RepositoryImp(api, db, connectionUtil)
        }
    }
}


private class RepositoryImp(
    val api: Api,
    val db: AppDatabase,
    val connectionUtil: ConnectionUtil
) : Repository {

    override suspend fun searchMovies(key: String): List<Movie> {
        if (!connectionUtil.isOnline) {
            return db.movieDao().searchByTitle(key)
        }

        val aMovies = api.searchMovies(key)
        val movies = mapAMoviesToEntity(aMovies)
        db.movieDao().insertAll(movies)
        return movies
    }

    override suspend fun getMovieByImdbId(imdbId: String): Movie? {
        var result = db.movieDao().getByImdbId(imdbId)
        if (!connectionUtil.isOnline) {
            return result
        }

        if (result?.details == null) {
            val aMovie = api.getMovieByImdbId(imdbId)
            result = mapAMovieToEntity(aMovie)
            db.movieDao().upsert(result)
        }

        return result
    }

    override suspend fun updateMovie(movie: Movie) {
        db.movieDao().upsert(movie)
    }

    override fun fetchWatchedListMovies(limit: Int): LiveData<List<Movie>> {
        return db.movieDao().getWatchedList(limit)
    }

    override fun fetchWatchlistMovies(limit: Int): LiveData<List<Movie>> {
        return db.movieDao().getWatchlist(limit)
    }

    override fun fetchFavoriteMovies(limit: Int): LiveData<List<Movie>> {
        return db.movieDao().getFavorites(limit)
    }

    private fun mapAMoviesToEntity(aMovies: List<AMovie>): List<Movie> {
        return aMovies.map { mapAMovieToEntity(it) }
    }

    private fun mapAMovieToEntity(aMovie: AMovie): Movie {
        return Movie(
            aMovie.imdbId,
            aMovie.poster,
            aMovie.title,
            aMovie.year,
            aMovie.type,
            Movie.Detail(
                aMovie.released,
                aMovie.runtime,
                aMovie.genre,
                aMovie.director,
                aMovie.writer,
                aMovie.actors,
                aMovie.plot,
                aMovie.language,
                aMovie.awards,
                aMovie.production,
                aMovie.imdbRating,
                aMovie.country,
                aMovie.rated
            )
        )
    }

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface RepositoryEntryPoint {

//        @Provides
//        fun providesRepo(): Repository{
////            Repository.Builder(AppDatabase.getInstance()).build()
//        }
    }
}