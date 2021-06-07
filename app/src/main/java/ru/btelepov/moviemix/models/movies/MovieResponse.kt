package ru.btelepov.moviemix.models.movies

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    var page: Int?,
    var results: List<MovieData>?,
    @SerializedName("total_results")
    var totalResults: Int?,
    @SerializedName("total_pages")
    var totalPages: Int?
)
