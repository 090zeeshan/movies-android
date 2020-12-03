package com.vd.movies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_details")
data class MovieDetail(
    @PrimaryKey
    @SerializedName("imdbID")
    var imdbId: String,

    @SerializedName("Title")
    var title: String,

    @SerializedName("Year")
    var year: String,

    @SerializedName("Released")
    var released: String,

    @SerializedName("Runtime")
    var runtime: String,

    @SerializedName("Genre")
    var genre: String,

    @SerializedName("Director")
    var director: String,

    @SerializedName("Writer")
    var writer: String,

    @SerializedName("Actors")
    var actors: String,

    @SerializedName("Plot")
    var plot: String,

    @SerializedName("Language")
    var language: String,

    @SerializedName("Awards")
    var awards: String,

    @SerializedName("Poster")
    var poster: String,

    @SerializedName("Production")
    var production: String,

    @SerializedName("Type")
    var type: String,

    @SerializedName("imdbRating")
    var imdbRating: String,

    @SerializedName("Country")
    var country: String,

    @SerializedName("Rated")
    var rated: String,

    var isAddedToWatchList: Boolean = false,
    var isAddedToWatchedList: Boolean = false,
    var isAddedToFavorites: Boolean = false
)