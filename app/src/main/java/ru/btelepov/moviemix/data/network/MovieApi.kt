package ru.btelepov.moviemix.data.network

import retrofit2.Response
import retrofit2.http.*
import ru.btelepov.moviemix.models.movies.MovieData
import ru.btelepov.moviemix.models.movies.MovieResponse
import ru.btelepov.moviemix.models.credits.CreditsResponse
import ru.btelepov.moviemix.utils.Constants.Companion.ACCOUNT_ID

// TODO: 15.05.2021  @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API


interface MovieApi {



    @GET("3/account/{account_id}/favorite/movies")
    suspend fun getFavoriteMoviesFromApi(
        @Path("account_id") account_id: Int = ACCOUNT_ID,
        @Query("session_id") session_id: String,
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/popular")
    suspend fun getPopularMovies(

        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>


    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(

        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>


    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>





    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String = "ru"
    ): Response<CreditsResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String = "ru"
    ): Response<MovieData>
}