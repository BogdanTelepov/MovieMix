package ru.btelepov.moviemix.viewmodels

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.btelepov.moviemix.data.repository.Repository
import ru.btelepov.moviemix.data.database.enteties.SerialEntity
import ru.btelepov.moviemix.models.serials.SerialResponse


import ru.btelepov.moviemix.utils.Functions.Companion.hasInternetConnection
import ru.btelepov.moviemix.utils.NetworkResult
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SerialsMainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    /** Room Database */

    val readSerialItems: LiveData<List<SerialEntity>> =
        repository.locale.readSerialItems().asLiveData()

    private fun insertSerialItem(serialEntity: SerialEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.locale.insertSerialItem(serialEntity)
        }


    /** RETROFIT */
    var serialListResponse: MutableLiveData<NetworkResult<SerialResponse>> = MutableLiveData()
    var pages = 1

    var serialResponse: SerialResponse? = null

//    fun getTvSerials(): Flow<PagingData<SerialItem>> {
//        return repository.remote.getTvSerials().cachedIn(viewModelScope)
//    }


    fun getSerialList() {
        viewModelScope.launch {
            getListSerialSafeCall()
        }
    }


    private suspend fun getListSerialSafeCall() {
        serialListResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {

                val response = repository.remote.getListSerials(page = pages)
                serialListResponse.value = handleSerialResponse(response)
                val serialList = serialListResponse.value!!.data
                if (serialList != null) {
                    offlineCacheSerials(serialList)
                }
            } catch (t: Exception) {
                when (t) {
                    is IOException -> serialListResponse.value =
                        NetworkResult.Error("No Internet Connection :(")
                    else -> serialListResponse.value = NetworkResult.Error("Movie Not Found")
                }
            }
        }
    }


    private fun handleSerialResponse(response: Response<SerialResponse>): NetworkResult<SerialResponse> {
        if (response.isSuccessful) {

            response.body()?.let {
                pages++
                if (serialResponse == null) {
                    serialResponse = it
                } else {
                    val oldList = serialResponse?.serialItems
                    val newList = it.serialItems
                    oldList?.addAll(newList)
                }

                return NetworkResult.Success(serialResponse ?: it)

            }
        }

        return NetworkResult.Error(response.message().toString())

    }

    private fun offlineCacheSerials(serialResponse: SerialResponse) {
        val serialEntity = SerialEntity(serialResponse)
        insertSerialItem(serialEntity)
    }

}