package ru.gb.veber.materialdesignapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gb.veber.materialdesignapp.utils.NASA_API_APOD
import ru.gb.veber.materialdesignapp.utils.NASA_API_EPIC
import ru.gb.veber.materialdesignapp.utils.NASA_API_MARS

interface PictureApi {

    @GET(NASA_API_APOD)
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String,
    ): Call<PictureDTO>

    @GET(NASA_API_APOD)
    fun getListPicture(
        @Query("api_key") apiKey: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Call<List<PictureDTO>>

    @GET(NASA_API_EPIC)
    fun getEPIC(
        @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>

    @GET(NASA_API_MARS)
    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>
}