package com.vd.movies.repository.db.dao

import androidx.room.*
import com.vd.movies.repository.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(movie: Movie)

    @Query("SELECT * FROM movies WHERE imdbId = :imdbId")
    fun getByImdbId(imdbId: String): Movie?

    @Query("SELECT * FROM movies WHERE isAddedToWatchList = 1")
    fun getWatchlist(): List<Movie>

    @Query("SELECT * FROM movies WHERE isAddedToWatchedList = 1")
    fun getWatchedList(): List<Movie>

    @Query("SELECT * FROM movies WHERE isAddedToFavorites = 1")
    fun getFavorites(): List<Movie>
}