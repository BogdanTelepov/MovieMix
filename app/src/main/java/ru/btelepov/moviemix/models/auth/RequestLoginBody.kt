package ru.btelepov.moviemix.models.auth


import com.google.gson.annotations.SerializedName

data class RequestLoginBody constructor(
    @SerializedName("password")
    val password: String,
    @SerializedName("request_token")
    val requestToken: String,
    @SerializedName("username")
    val username: String
)