package com.vd.movies.repository.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Search")
    val movies: List<Movie>?
)