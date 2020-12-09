package com.vd.movies.data.api.model

import com.google.gson.annotations.SerializedName

data class ASearchResult(
    @SerializedName("Search")
    val movies: List<AMovie>?
)