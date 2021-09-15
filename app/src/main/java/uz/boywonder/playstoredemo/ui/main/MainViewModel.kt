package uz.boywonder.playstoredemo.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.boywonder.playstoredemo.data.Repository
import uz.boywonder.playstoredemo.model.ImageItem
import uz.boywonder.playstoredemo.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private var _imageLiveData: MutableLiveData<NetworkResult<List<ImageItem>>> = MutableLiveData()
    val moviesLiveData: LiveData<NetworkResult<List<ImageItem>>> get() = _imageLiveData

    /* RETROFIT */

    fun getResponse() = viewModelScope.launch {
        getResponseSafeCall()
    }

    private suspend fun getResponseSafeCall() {
        _imageLiveData.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getImageList()
                _imageLiveData.value = handleResponse(response)
            } catch (e: Exception) {
                _imageLiveData.value = NetworkResult.Error("Something went wrong.")
                Log.e("MainViewModel", e.message.toString())
            }
        } else {
            _imageLiveData.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleResponse(
        response: Response<List<ImageItem>>
    ): NetworkResult<List<ImageItem>> {

        return when {
            response.body().isNullOrEmpty() -> {
                NetworkResult.Error("No Image List Found.")
            }
            response.isSuccessful -> {
                val imageListData = response.body()
                NetworkResult.Success(imageListData!!)
            }
            else -> NetworkResult.Error(response.message())
        }
    }

    var isOffline = false

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}