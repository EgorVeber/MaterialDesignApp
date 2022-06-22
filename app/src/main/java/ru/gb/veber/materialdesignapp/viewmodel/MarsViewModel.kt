package ru.gb.veber.materialdesignapp.viewmodel

import AppState
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.veber.materialdesignapp.BuildConfig
import ru.gb.veber.materialdesignapp.model.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MarsViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: PictureRetrofit = PictureRetrofit()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getMarsPicture() {
        liveDataToObserve.postValue(AppState.Loading(null))
        val earthDate = getDayBeforeYesterday()
        retrofitImpl.getMarsPictureByDate(earthDate, BuildConfig.NASA_API_KEY, marsCallback)
    }

    fun getDayBeforeYesterday(): String {

        val cal: Calendar = Calendar.getInstance()
        val s = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DAY_OF_YEAR, -2)
        return s.format(cal.time)

    }

    val marsCallback = object : Callback<MarsPhotosServerResponseData> {
        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(AppState.SuccessMars(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(AppState.Error(Throwable(UNKNOWN_ERROR), "error"))
                } else {
                    liveDataToObserve.postValue(AppState.Error(Throwable(message), "error"))
                }
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(t, "error"))
        }
    }

    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOWN_ERROR = "Unidentified error"
    }
}