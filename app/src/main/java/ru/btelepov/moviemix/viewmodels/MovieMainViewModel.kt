package ru.btelepov.moviemix.viewmodels

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.btelepov.moviemix.data.repository.Repository
import ru.btelepov.moviemix.data.database.enteties.MovieEntity
import ru.btelepov.moviemix.data.database.enteties.PopularMovieEntity
import ru.btelepov.moviemix.data.database.enteties.TopRatedMovieEntity
import ru.btelepov.moviemix.models.movies.MovieResponse
import ru.btelepov.moviemix.utils.Functions.Companion.handleMovieResponse
import ru.btelepov.moviemix.utils.Functions.Companion.hasInternetConnection

import ru.btelepov.moviemix.utils.NetworkResult
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieMainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    /** Room Database */

    val readMovieItems: LiveData<List<MovieEntity>> =
        repository.locale.readMovieItems().asLiveData()

    val readPopularMovies: LiveData<List<PopularMovieEntity>> =
        repository.locale.readPopularMovies().asLiveData()
    val readTopRatedMovies: LiveData<List<TopRatedMovieEntity>> =
        repository.locale.readTopRatedMovies().asLiveData()


    private fun insertMovieItem(movieEntity: MovieEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.locale.insertMovieItem(movieEntity)
        }

    private fun insertPopularMovieItem(popularMovieEntity: PopularMovieEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.locale.insertPopularMovieItem(popularMovieEntity)
        }

    private fun insertTopRatedMovieItem(topRatedMovieEntity: TopRatedMovieEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.locale.insertTopRatedMovieItem(topRatedMovieEntity)
        }


    /** RETROFIT */
    var popularMoviesResponse: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()
    var topRatedMoviesResponse: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()
    var nowPlayingMoviesResponse: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()


    fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesSafeCall()
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            getTopRatedMoviesSafeCall()
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlayingMoviesSafeCall()
        }
    }

    private suspend fun getNowPlayingMoviesSafeCall() {
        nowPlayingMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getNowPlayingMovies()
                nowPlayingMoviesResponse.value = handleMovieResponse(response)
            } catch (e: Exception) {
                nowPlayingMoviesResponse.value = NetworkResult.Error("Movie Not Found")
            }
        } else {
            nowPlayingMoviesResponse.value = NetworkResult.Error("No Internet Connection :(")
        }
    }

    private suspend fun getTopRatedMoviesSafeCall() {
        topRatedMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getTopRatedMovies()
                topRatedMoviesResponse.value = handleMovieResponse(response)

                val topRatedMovie = topRatedMoviesResponse.value!!.data
                if (topRatedMovie != null) {
                    offlineCacheTopRatedMovies(topRatedMovie)
                }
            } catch (e: Exception) {
                topRatedMoviesResponse.value = NetworkResult.Error("Movie Not Found")
            }
        } else {
            topRatedMoviesResponse.value = NetworkResult.Error("No Internet Connection :(")
        }
    }

    private suspend fun getPopularMoviesSafeCall() {
        popularMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getPopularMovies()
                popularMoviesResponse.value = handleMovieResponse(response)

                val popularMovie = popularMoviesResponse.value!!.data
                if (popularMovie != null) {
                    offlineCachePopularMovies(popularMovie)
                }

            } catch (e: Exception) {
                popularMoviesResponse.value = NetworkResult.Error("Movie Not Found")
            }
        } else {
            popularMoviesResponse.value = NetworkResult.Error("No Internet Connection :(")
        }
    }



    private fun offlineCacheTopRatedMovies(movieResponse: MovieResponse) {
        val topRatedMovieEntity = TopRatedMovieEntity(movieResponse)
        insertTopRatedMovieItem(topRatedMovieEntity)
    }

    private fun offlineCachePopularMovies(movieResponse: MovieResponse) {
        val popularMovies = PopularMovieEntity(movieResponse)
        insertPopularMovieItem(popularMovies)
    }


}