package ru.btelepov.moviemix.data


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.btelepov.moviemix.data.network.MovieApi
import ru.btelepov.moviemix.models.MovieResponse
import ru.btelepov.moviemix.models.serials.SerialItem
import ru.btelepov.moviemix.models.serials.SerialResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApi: MovieApi) {

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
        ) { SerialsPagingSource(movieApi) }.flow
    }


    suspend fun getListSerials(page: Int): Response<SerialResponse> {
        return movieApi.getTvPopular(page = page)
    }

}