package ru.btelepov.moviemix.models.auth

import com.google.gson.annotations.SerializedName

data class NewSessionResponse constructor(

    @SerializedName("success")
    val success: Boolean,
    @SerializedName("session_id")
    val session_id: String
)
