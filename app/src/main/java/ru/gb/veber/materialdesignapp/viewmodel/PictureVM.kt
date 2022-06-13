package com.gb.m_1975_3.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.veber.materialdesignapp.BuildConfig.NASA_API_KEY
import ru.gb.veber.materialdesignapp.model.PictureDTO
import ru.gb.veber.materialdesignapp.model.PictureRetrofit

class PictureVM(
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val pictureRetrofit: PictureRetrofit = PictureRetrofit()
) : ViewModel() {

    fun sendServerReques2(date: String) {
        liveData.postValue(AppState.Loading(null))
        pictureRetrofit.getPicture2(callback, date)
    }

    private val callback = object : Callback<PictureDTO> {
        override fun onResponse(
            call: Call<PictureDTO>,
            response: Response<PictureDTO>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(AppState.Success(it))
                }
            } else {
                //TODO HW
            }
        }

        override fun onFailure(call: Call<PictureDTO>, t: Throwable) {
            //TODO HW
        }
    }
}