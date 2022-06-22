package ru.gb.veber.materialdesignapp.viewmodel

import AppState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.veber.materialdesignapp.BuildConfig
import ru.gb.veber.materialdesignapp.model.EarthEpicServerResponseData
import ru.gb.veber.materialdesignapp.model.PictureRetrofit

class EarthViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: PictureRetrofit = PictureRetrofit()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getEpic() {
        liveDataToObserve.postValue(AppState.Loading(null))
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AppState.Error(Throwable(API_ERROR), "error")
        } else {
            retrofitImpl.getEPIC(apiKey, epicCallback)
        }
    }

    private val epicCallback = object : Callback<List<EarthEpicServerResponseData>> {

        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(AppState.SuccessEarthEpic(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(AppState.Error(Throwable(UNKNOWN_ERROR), "error"))
                } else {
                    liveDataToObserve.postValue(AppState.Error(Throwable(message), "error"))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(t, "error"))
        }
    }


    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOWN_ERROR = "Unidentified error"
    }
}