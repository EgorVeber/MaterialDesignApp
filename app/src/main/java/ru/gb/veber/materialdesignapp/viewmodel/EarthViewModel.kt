package ru.gb.veber.materialdesignapp.viewmodel

import EarthState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.veber.materialdesignapp.BuildConfig
import ru.gb.veber.materialdesignapp.model.EarthEpicServerResponseData
import ru.gb.veber.materialdesignapp.model.PictureRetrofit
import ru.gb.veber.materialdesignapp.utils.EMPTY_RESPONSE
import ru.gb.veber.materialdesignapp.utils.ERROR_FAILURE

class EarthViewModel(
    private val liveDataToObserve: MutableLiveData<EarthState> = MutableLiveData(),
    private val retrofitImpl: PictureRetrofit = PictureRetrofit()
) : ViewModel() {

    fun getLiveData(): LiveData<EarthState> {
        return liveDataToObserve
    }

    fun getEpic() {
        liveDataToObserve.postValue(EarthState.Loading(null))
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            EarthState.Error(Throwable(), ERROR_FAILURE)
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
                liveDataToObserve.postValue(EarthState.Success(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(EarthState.Error(Throwable(), EMPTY_RESPONSE))
                } else {
                    liveDataToObserve.postValue(EarthState.Error(Throwable(), ERROR_FAILURE))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveDataToObserve.postValue(EarthState.Error(t, ERROR_FAILURE))
        }
    }
}