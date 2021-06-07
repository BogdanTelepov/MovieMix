package ru.btelepov.moviemix.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response
import ru.btelepov.moviemix.MyApplication
import ru.btelepov.moviemix.models.movies.MovieResponse
import ru.btelepov.moviemix.utils.Constants.Companion.APP_ACTIVITY

class Functions {

    companion object {
        fun hasInternetConnection(): Boolean {
            val connectivityManager = MyApplication.getContext()?.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }


        fun  handleMovieResponse(response: Response<MovieResponse>): NetworkResult<MovieResponse> {
            when {
                response.message().toString().contains("timeout") -> {
                    return NetworkResult.Error("Timeout")
                }
                response.code() == 402 -> {
                    return NetworkResult.Error("API Key Limited")
                }

                response.body()?.results.isNullOrEmpty()->{
                    return NetworkResult.Error("Movie Not Found")
                }

                response.isSuccessful -> {
                    val movie = response.body()
                    return NetworkResult.Success(movie!!)
                }
                else -> {
                    return NetworkResult.Error(response.message())
                }
            }
        }


        fun showToast(message: String) {
            Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
        }

        fun showSnackBar(message: String, view: View) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        }


        fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
            observe(lifecycleOwner, object : Observer<T> {
                override fun onChanged(t: T) {
                    removeObserver(this)
                    observer.onChanged(t)
                }

            })
        }

    }
}