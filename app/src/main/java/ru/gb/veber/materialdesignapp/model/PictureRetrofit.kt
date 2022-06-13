package ru.gb.veber.materialdesignapp.model

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.veber.materialdesignapp.BuildConfig
import ru.gb.veber.materialdesignapp.utils.NASA_BASE_URL

class PictureRetrofit {

    fun getPicture(callback: retrofit2.Callback<PictureDTO>, date: String) {
        pictureApi.getPictureOfTheDay(BuildConfig.NASA_API_KEY, date).enqueue(callback)
    }

    private val pictureApi = Retrofit.Builder().baseUrl(NASA_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(PictureApi::class.java)
}