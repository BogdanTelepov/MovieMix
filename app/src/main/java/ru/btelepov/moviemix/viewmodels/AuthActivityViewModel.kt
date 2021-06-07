package ru.btelepov.moviemix.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.btelepov.moviemix.data.repository.Repository
import ru.btelepov.moviemix.models.auth.*

import ru.btelepov.moviemix.utils.Functions.Companion.hasInternetConnection
import ru.btelepov.moviemix.utils.NetworkResult
import javax.inject.Inject

@HiltViewModel
class AuthActivityViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    var requestTokenResponse: MutableLiveData<NetworkResult<RequestTokenResponse>> =
        MutableLiveData()
    var sessionWithLoginResponse: MutableLiveData<NetworkResult<SessionWithLoginResponse>> =
        MutableLiveData()

    var sessionIdResponse: MutableLiveData<NetworkResult<NewSessionResponse>> =
        MutableLiveData()

    fun getSessionId(sessionId: SessionId) {
        viewModelScope.launch {
            createSessionId(sessionId)
        }
    }

    fun createToken() {
        viewModelScope.launch {
            createRequestToken()
        }
    }

    fun login(requestLoginBody: RequestLoginBody) {
        viewModelScope.launch {

            createSessionWithLogin(requestLoginBody)

        }
    }


    private suspend fun createSessionId(sessionId: SessionId) {
        sessionIdResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.createSession(sessionId)
                sessionIdResponse.value = handleAllResponses(response)
            } catch (e: Exception) {
                sessionIdResponse.value = NetworkResult.Error("Session ID isn't created")
            }
        } else {
            sessionIdResponse.value = NetworkResult.Error("No Internet Connection :(")
        }
    }





    private suspend fun createSessionWithLogin(requestLoginBody: RequestLoginBody) {
        sessionWithLoginResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.createSessionWithLogin(requestLoginBody)
                sessionWithLoginResponse.value = handleAllResponses(response)
            } catch (e: Exception) {
                sessionWithLoginResponse.value = NetworkResult.Error("Login Wrong")
            }
        } else {
            sessionWithLoginResponse.value = NetworkResult.Error("No Internet Connection :(")
        }

    }




    private suspend fun createRequestToken() {
        requestTokenResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.createRequestToken()
                requestTokenResponse.value = handleAllResponses(response = response)
            } catch (e: Exception) {
                requestTokenResponse.value = NetworkResult.Error("Token isn't created")
            }
        } else {
            requestTokenResponse.value = NetworkResult.Error("No Internet Connection :(")
        }
    }




    private fun <T> handleAllResponses(response: Response<T>): NetworkResult<T> {
        return when {
            response.isSuccessful -> {
                val responseBody = response.body()
                NetworkResult.Success(responseBody!!)
            }

            response.message().toString().contains("timeout")->{
                NetworkResult.Error("TimeOut")
            }

            response.code()==402 ->{
                NetworkResult.Error("API Key Limited")
            }

            response.code() == 401 -> {
                NetworkResult.Error("Invalid API key: You must be granted a valid key")
            }
            else ->
                NetworkResult.Error(response.message())
        }
    }


}