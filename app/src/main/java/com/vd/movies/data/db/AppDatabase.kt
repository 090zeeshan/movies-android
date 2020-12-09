package com.vd.movies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vd.movies.data.db.dao.MovieDao
import com.vd.movies.data.db.entity.Movie

private const val DB_VER = 1
private const val DB_NAME = "MoviesDatabase"


@Database(entities = [Movie::class], version = DB_VER)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
            }
            return instance!!
        }
    }
}

