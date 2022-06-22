package ru.gb.veber.materialdesignapp.model

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.veber.materialdesignapp.BuildConfig
import ru.gb.veber.materialdesignapp.utils.NASA_BASE_URL

class PictureRetrofit {

    private val pictureApi = Retrofit.Builder().baseUrl(NASA_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(PictureApi::class.java)

    fun getPicture(callback: Callback<PictureDTO>, date: String) {
        pictureApi.getPictureOfTheDay(BuildConfig.NASA_API_KEY, date).enqueue(callback)
    }

    fun getEPIC(apiKey: String, epicCallback: Callback<List<EarthEpicServerResponseData>>) {
        pictureApi.getEPIC(apiKey).enqueue(epicCallback)
    }

    fun getMarsPictureByDate(
        earth_date: String,
        apiKey: String,
        marsCallbackByDate: Callback<MarsPhotosServerResponseData>
    ) {
        pictureApi.getMarsImageByDate(earth_date, apiKey).enqueue(marsCallbackByDate)
    }
}