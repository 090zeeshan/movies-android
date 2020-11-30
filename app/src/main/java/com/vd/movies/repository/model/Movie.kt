package com.vd.movies.repository.model

import com.google.gson.annotations.SerializedName

data class Movie(
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

    var isAddedToWatchList: Boolean = false,
    var isAddedToWatchedList: Boolean = false,
    var isAddedToFavorites: Boolean = false
)