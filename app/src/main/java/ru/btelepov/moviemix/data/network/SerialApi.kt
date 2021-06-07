package ru.btelepov.moviemix.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.btelepov.moviemix.models.serials.SerialResponse

interface SerialApi {

    @GET("3/tv/popular")
    suspend fun getTvPopular(
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1

    ): Response<SerialResponse>
}