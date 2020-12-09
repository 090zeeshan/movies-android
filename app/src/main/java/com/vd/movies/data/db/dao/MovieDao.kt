package com.vd.movies.data.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vd.movies.data.db.entity.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(movie: Movie)

    @Query("SELECT * FROM movies WHERE title LIKE '%' ||:title || '%'")
    fun searchByTitle(title: String): List<Movie>

    @Query("SELECT * FROM movies WHERE imdbId = :imdbId")
    fun getByImdbId(imdbId: String): Movie?

    @Query("SELECT * FROM movies WHERE isAddedToWatchList = 1 LIMIT :limit")
    fun getWatchlist(limit: Int): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE isAddedToWatchedList = 1 LIMIT :limit")
    fun getWatchedList(limit: Int): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE isAddedToFavorites = 1 LIMIT :limit")
    fun getFavorites(limit: Int): LiveData<List<Movie>>
}