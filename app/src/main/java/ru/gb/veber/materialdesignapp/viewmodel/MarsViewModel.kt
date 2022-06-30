package ru.gb.veber.materialdesignapp.viewmodel


import MarsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.veber.materialdesignapp.BuildConfig
import ru.gb.veber.materialdesignapp.model.MarsPhotosServerResponseData
import ru.gb.veber.materialdesignapp.model.PictureRetrofit
import ru.gb.veber.materialdesignapp.utils.EMPTY_RESPONSE
import ru.gb.veber.materialdesignapp.utils.ERROR_FAILURE
import java.text.SimpleDateFormat
import java.util.*

class MarsViewModel(
    private val liveDataToObserve: MutableLiveData<MarsState> = MutableLiveData(),
    private val retrofitImpl: PictureRetrofit = PictureRetrofit()
) : ViewModel() {

    fun getLiveData(): LiveData<MarsState> {
        return liveDataToObserve
    }

    fun getMarsPicture() {
        liveDataToObserve.postValue(MarsState.Loading(null))
        val earthDate = getDayBeforeYesterday()
        retrofitImpl.getMarsPictureByDate(earthDate, BuildConfig.NASA_API_KEY, marsCallback)
    }

    fun getDayBeforeYesterday(): String {

        val cal: Calendar = Calendar.getInstance()
        val s = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DAY_OF_YEAR, -2)
        return s.format(cal.time)

    }

    private val marsCallback = object : Callback<MarsPhotosServerResponseData> {
        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(MarsState.Success(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(MarsState.Error(Throwable(), EMPTY_RESPONSE))
                } else {
                    liveDataToObserve.postValue(MarsState.Error(Throwable(), ERROR_FAILURE))
                }
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(MarsState.Error(t, ERROR_FAILURE))
        }
    }
}