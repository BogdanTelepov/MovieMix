package ru.btelepov.moviemix.models.serials


import com.google.gson.annotations.SerializedName

data class SerialResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val serialItems: MutableList<SerialItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)