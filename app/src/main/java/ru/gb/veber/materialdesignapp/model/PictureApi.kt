package ru.gb.veber.materialdesignapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureApi {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String,
    ): Call<PictureDTO>

    @GET("planetary/apod")
    fun getListPicture(
        @Query("api_key") apiKey: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Call<List<PictureDTO>>
}