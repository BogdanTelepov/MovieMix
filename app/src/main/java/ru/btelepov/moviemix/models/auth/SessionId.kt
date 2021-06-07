package ru.btelepov.moviemix.models.auth

import com.google.gson.annotations.SerializedName

data class SessionId(
    @SerializedName("request_token")
    var requestToken: String = ""
)
