package ru.btelepov.moviemix.models.auth

import com.google.gson.annotations.SerializedName

data class SessionWithLoginResponse constructor(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("expires_at")
    val expires_at: String,
    @SerializedName("request_token")
    val request_token: String

)
