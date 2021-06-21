package com.vd.movies.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    var imdbId: String,

    var poster: String,

    var title: String,

    var year: String,

    var type: String,

    @Embedded
    val details: Detail?,

    var isAddedToWatchList: Boolean = false,

    var isAddedToWatchedList: Boolean = false,

    var isAddedToFavorites: Boolean = false
) {
    data class Detail(
        var released: String?,

        var runtime: String?,

        var genre: String?,

        var director: String?,

        var writer: String?,

        var actors: String?,

        var plot: String?,

        var language: String?,

        var awards: String?,

        var production: String?,

        var imdbRating: String?,

        var country: String?,

        var rated: String?
    )
}