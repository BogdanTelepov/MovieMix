package ru.btelepov.moviemix.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.btelepov.moviemix.BuildConfig
import ru.btelepov.moviemix.models.MovieResponse
import ru.btelepov.moviemix.models.serials.SerialResponse

interface MovieApi {

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>


    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>


    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>


    @GET("3/tv/popular")
    suspend fun getTvPopular(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1

    ): Response<SerialResponse>
}