package ru.btelepov.moviemix.models.auth


import com.google.gson.annotations.SerializedName

data class RequestTokenResponse constructor(


    @SerializedName("expires_at")
    val expiresAt: String,

    @SerializedName("request_token")
    val requestToken: String,

    @SerializedName("success")
    val success: Boolean
)