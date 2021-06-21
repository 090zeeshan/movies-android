package com.vd.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vd.movies.data.db.dao.MovieDao
import com.vd.movies.data.db.entity.Movie

private const val DB_VER = 1
const val DB_NAME = "MoviesDatabase"


@Database(entities = [Movie::class], version = DB_VER)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}


