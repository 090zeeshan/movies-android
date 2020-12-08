package com.vd.movies.data.db.dao

import androidx.room.*
import com.vd.movies.data.model.MovieDetail

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(movie: MovieDetail)

    @Query("SELECT * FROM movie_details WHERE imdbId = :imdbId")
    fun getByImdbId(imdbId: String): MovieDetail?

    @Query("SELECT * FROM movie_details WHERE isAddedToWatchList = 1 LIMIT :limit")
    fun getWatchlist(limit: Int): List<MovieDetail>

    @Query("SELECT * FROM movie_details WHERE isAddedToWatchedList = 1 LIMIT :limit")
    fun getWatchedList(limit: Int): List<MovieDetail>

    @Query("SELECT * FROM movie_details WHERE isAddedToFavorites = 1 LIMIT :limit")
    fun getFavorites(limit: Int): List<MovieDetail>
}