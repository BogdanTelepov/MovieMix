package ru.btelepov.moviemix.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

import ru.btelepov.moviemix.data.repository.Repository
import ru.btelepov.moviemix.models.movies.MovieData
import ru.btelepov.moviemix.models.movies.MovieResponse
import ru.btelepov.moviemix.models.credits.CreditsResponse
import ru.btelepov.moviemix.utils.Functions.Companion.handleMovieResponse
import ru.btelepov.moviemix.utils.Functions.Companion.hasInternetConnection
import ru.btelepov.moviemix.utils.NetworkResult
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    var creditsResponse: MutableLiveData<NetworkResult<CreditsResponse>> = MutableLiveData()
    var similarMoviesResponse: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()
    var getMovieDetailsResponse: MutableLiveData<NetworkResult<MovieData>> = MutableLiveData()

    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            getSimilarMoviesSafeCall(movieId)
        }
    }


    fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            getMovieCreditsSafeCall(movieId)
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailSafeCall(movieId)
        }
    }

    private suspend fun getSimilarMoviesSafeCall(movieId: Int) {
        similarMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getSimilarMovies(movieId)
                similarMoviesResponse.value = handleMovieResponse(response)
            } catch (e: Exception) {
                similarMoviesResponse.value = NetworkResult.Error("No Data")
            }

        } else {
            similarMoviesResponse.value = NetworkResult.Error("No Internet Connection :(")
        }
    }


    private suspend fun getMovieCreditsSafeCall(movieId: Int) {

        creditsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getMovieCredits(movieId)
                creditsResponse.value = handleMovieCredits(response)
            } catch (e: Exception) {
                creditsResponse.value = NetworkResult.Error("No Data")
            }
        } else {
            creditsResponse.value = NetworkResult.Error("No Internet Connection :(")
        }
    }

    private suspend fun getMovieDetailSafeCall(movieId: Int) {
        getMovieDetailsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getMovieDetails(movieId)
                getMovieDetailsResponse.value = handleMovieDetailsResponse(response)
            } catch (e: Exception) {
                getMovieDetailsResponse.value = NetworkResult.Error("No data")
            }
        } else {
            getMovieDetailsResponse.value = NetworkResult.Error("No Internet Connection :(")
        }
    }


    private fun handleMovieDetailsResponse(response: Response<MovieData>): NetworkResult<MovieData> {
        return when {
            response.message().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error("API Key Limited")
            }
            response.isSuccessful -> {
                val movieDetail = response.body()
                NetworkResult.Success(movieDetail!!)
            }

            else ->
                NetworkResult.Error(response.message())
        }

    }

    private fun handleMovieCredits(response: Response<CreditsResponse>): NetworkResult<CreditsResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("TimeOut")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }
            response.body()?.cast.isNullOrEmpty() -> {
                return NetworkResult.Error("No Data")
            }

            response.isSuccessful -> {
                val cast = response.body()
                return NetworkResult.Success(cast!!)
            }

            else ->
                return NetworkResult.Error(response.message())
        }
    }


}