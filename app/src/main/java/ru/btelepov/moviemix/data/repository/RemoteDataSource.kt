package ru.btelepov.moviemix.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.btelepov.moviemix.data.paging.SerialsPagingSource
import ru.btelepov.moviemix.data.network.AuthorizationApi
import ru.btelepov.moviemix.data.network.MovieApi
import ru.btelepov.moviemix.data.network.SerialApi
import ru.btelepov.moviemix.models.movies.MovieData
import ru.btelepov.moviemix.models.movies.MovieResponse
import ru.btelepov.moviemix.models.auth.*
import ru.btelepov.moviemix.models.credits.CreditsResponse
import ru.btelepov.moviemix.models.serials.SerialItem
import ru.btelepov.moviemix.models.serials.SerialResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieApi: MovieApi,
    private val authApi: AuthorizationApi,
    private val serialApi: SerialApi
) {


    suspend fun createRequestToken(): Response<RequestTokenResponse> {
        return authApi.createRequestToken()
    }

    suspend fun createSessionWithLogin(requestLoginBody: RequestLoginBody): Response<SessionWithLoginResponse> {
        return authApi.createSessionWithLogin(requestLoginBody)
    }

    suspend fun createSession(sessionId: SessionId): Response<NewSessionResponse> {
        return authApi.createSession(sessionId)
    }


    suspend fun getPopularMovies(): Response<MovieResponse> {
        return movieApi.getPopularMovies()
    }

    suspend fun getTopRatedMovies(): Response<MovieResponse> {
        return movieApi.getTopRatedMovies()
    }

    suspend fun getNowPlayingMovies(): Response<MovieResponse> {
        return movieApi.getNowPlayingMovies()
    }

    fun getTvSerials(): Flow<PagingData<SerialItem>> {
        return Pager(
            PagingConfig(
                5,
                enablePlaceholders = false
            )
        ) { SerialsPagingSource(serialApi) }.flow
    }

    suspend fun getSimilarMovies(movieId: Int): Response<MovieResponse> {
        return movieApi.getSimilarMovies(movieId)

    }

    suspend fun getMovieDetails(movieId: Int): Response<MovieData> {
        return movieApi.getMovieDetail(movieId)
    }


    suspend fun getListSerials(page: Int): Response<SerialResponse> {
        return serialApi.getTvPopular(page = page)
    }

    suspend fun getMovieCredits(movieId: Int): Response<CreditsResponse> {
        return movieApi.getMovieCredits(movieId)

    }

}